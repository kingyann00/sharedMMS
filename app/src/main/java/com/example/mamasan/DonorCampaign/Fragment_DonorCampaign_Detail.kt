package com.example.mamasan_campaign.DonorCampaign

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan_campaign.CampaignCRUD.Campaign
import com.example.mamasan_campaign.CampaignCRUD.DonorCampaign
import com.example.mamasan_campaign.CampaignCRUD.DonorCampaignList
import com.example.mamasan_campaign.CampaignCRUD.Fragment_Campaign_DetailDirections
import com.example.mamasan_campaign.Database.DB_Campaign
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentCampaignDetailBinding
import com.example.mamasan.databinding.FragmentDonorCampaignDetailBinding
import org.json.JSONObject

class Fragment_DonorCampaign_Detail : Fragment() {
    private lateinit var binding: FragmentDonorCampaignDetailBinding
    private val args: Fragment_DonorCampaign_DetailArgs by navArgs()

    private val URLCheckBookingStatus: String = "http://10.0.2.2/mamasan/check_book_status.php"
    private val URLDonorCampaignDetail: String = "http://10.0.2.2/mamasan/retrieve_donee_campaign.php"
    private lateinit var donorCampaignList: ArrayList<DonorCampaign>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDonorCampaignDetailBinding.inflate(inflater, container, false)

        //hardcoded for doneeIDs
        val doneeID = "2"
        val campaignID = args.campaignID.toString()
        checkBookStatus(doneeID, campaignID)
        searchCampaign(campaignID)

        binding.bookCampaignButton.setOnClickListener {

            if (binding.bookCampaignButton.text == "Cancel Booking"){
                Log.i("CancelBookingPres", "Cancel Booking is pressed")
            }else{
                val builder = AlertDialog.Builder(activity)
                builder.setMessage("Are you sure you want to book ${binding.campaignDonordetailTitle.text} ?")
                builder.setTitle("Booking A Campaign !")
                builder.setCancelable(false)

                builder.setPositiveButton("Book") {
                        dialog, which ->
                    DB_Campaign().bookCampaign(requireContext(), campaignID.toString(), "3")
                    //change button color
                    binding.bookCampaignButton.setText("Cancel Booking")
                    binding.bookCampaignButton.setBackgroundColor(binding.bookCampaignButton.context.resources.getColor(R.color.color_reject))
                    val action = Fragment_DonorCampaign_DetailDirections.actionFragmentDonorCampaignDetailToFragmentDonorCampaignList()
                    findNavController().navigate(action)
                }
                builder.setNegativeButton("Cancel") {
                        dialog, which ->
                    dialog.cancel()
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }

        }

        return binding.root
    }

    //if campaign has been book, book button need to be changed to cancel booking button
    fun checkBookStatus(doneeID: String, campaignID: String) {
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLCheckBookingStatus,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0) {
                    Log.i("Empty", "Campaign Not Found")
                }
                for (i in 0 until jsonArray?.length()!!) {
                    val jsonObject = jsonArray?.optJSONObject(i)
                    if (jsonArray.length() - 1 == i) {
                        Log.i(
                            "Campaign Booked!!!",
                            "Campaign Already Booked"
                        )
                        binding.bookCampaignButton.setText("Cancel Booking")
                        binding.bookCampaignButton.setBackgroundColor(binding.bookCampaignButton.context.resources.getColor(R.color.color_reject))
                    }
                }
            }, Response.ErrorListener { error ->
                Log.e("Empty", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["donee_id"] = doneeID!!
                data["campaign_id"] = campaignID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    fun searchCampaign(campaignID: String) {
        donorCampaignList = ArrayList()

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLDonorCampaignDetail,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                donorCampaignList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0) {
                    Log.i("Empty", "Campaign Not Found")
                }
                for (i in 0 until jsonArray?.length()!!) {
                    val jsonObject = jsonArray?.optJSONObject(i)
                    donorCampaignList.add(
                        DonorCampaign(
                            jsonObject?.getInt("campaign_id"),
                            jsonObject?.getString("campaign_title"),
                            jsonObject?.getString("campaign_description"),
                            jsonObject?.getInt("max_booking"),
                            jsonObject?.getInt("participant"),
                            jsonObject?.getString("campaign_date"),
                            jsonObject?.getString("campaign_timeStart"),
                            jsonObject?.getString("campaign_timeEnd"),
                            jsonObject?.getString("campaign_state"),
                            jsonObject?.getString("campaign_address")
                        )
                    )

                    if (jsonArray.length() - 1 == i) {
                        Log.i(
                            "Campaign Detail",
                            "${donorCampaignList[0].campaign_title} Selected!!!"
                        )

                        binding.campaignDonordetailTitle.text = donorCampaignList[0].campaign_title
                        binding.campaignDonordetailDate.text = donorCampaignList[0].campaign_date
                        binding.campaignDonordetailLocation.text =
                            donorCampaignList[0].campaign_state + ", " + donorCampaignList[0].campaign_address
                        binding.campaignDonordetailTimeStartEnd.text =
                            donorCampaignList[0].campaign_time_start + " - " + donorCampaignList[0].campaign_time_end
                        //max people - participant count() = people left to participate
                        val peopleLeft = donorCampaignList[0].max_booking.toString()
                            .toInt() - donorCampaignList[0].participant.toString().toInt()
                        binding.campaignDonordetailPeopleLeft.text = peopleLeft.toString()
                        binding.campaignDonordetailDescription.text =
                            donorCampaignList[0].campaign_description
                        binding.campaignDonortdetailId.text =
                            donorCampaignList[0].campaign_id.toString()
                    }
                }
            }, Response.ErrorListener { error ->
                Log.e("Empty", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }
}