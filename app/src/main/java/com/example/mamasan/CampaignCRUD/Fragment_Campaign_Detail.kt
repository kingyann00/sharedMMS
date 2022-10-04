package com.example.mamasan_campaign.CampaignCRUD

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils.isEmpty
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
import com.example.mamasan_campaign.Database.DB_Campaign
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentCampaignDetailBinding
import org.json.JSONObject

class Fragment_Campaign_Detail : Fragment() {


    private lateinit var binding: FragmentCampaignDetailBinding
    private val args: Fragment_Campaign_DetailArgs by navArgs()

    private val URLCampaignDetail:String = "http://10.0.2.2/mamasan/campaign_detail.php"
    private lateinit var campaignList: ArrayList<Campaign>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCampaignDetailBinding.inflate(inflater, container, false)

        val campaignID = args.campaignId.toString()
        if(isEmpty(campaignID)){
            Log.w("Campaign Detail", "campaign id is empty")
        }else{
            Log.i("Campaign Detail", "ID: $campaignID")

        }

        searchCampaign(campaignID)
        binding.campaignEditButton.setOnClickListener{
            val campaignID = binding.campaignDetailId.text.toString().toInt()
            val action = Fragment_Campaign_DetailDirections.actionFragmentCampaignDetailToFragmentCampaignInformationEdit(campaignID)
            findNavController().navigate(action)
        }

        binding.campaignDeleteButton.setOnClickListener{
            val builder = AlertDialog.Builder(activity)

            builder.setMessage("Are you sure you want to delete ${binding.campaignDetailTitle.text} ?")
            builder.setTitle("Alert !")
            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
            builder.setCancelable(false)

            builder.setPositiveButton("Yes") {
                // When the user click yes button then app will close
                    dialog, which -> DB_Campaign().deleteCampaign(requireContext(), campaignID.toString())
                val action = Fragment_Campaign_DetailDirections.actionFragmentCampaignDetailToFragmentCampaignList2()
                findNavController().navigate(action)
            }

            builder.setNegativeButton("No") {
                // If user click no then dialog box is canceled.
                    dialog, which -> dialog.cancel()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun searchCampaign(campaignID: String){
        campaignList = ArrayList()
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLCampaignDetail,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                campaignList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Log.i("Empty","Campaign Not Found")
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    campaignList.add(
                        Campaign(
                            jsonObject?.getInt("campaign_id"),
                            jsonObject?.getString("campaign_title"),
                            jsonObject?.getString("campaign_description"),
                            jsonObject?.getInt("max_booking"),
                            jsonObject?.getString("campaign_date"),
                            jsonObject?.getString("campaign_timeStart"),
                            jsonObject?.getString("campaign_timeEnd"),
                            jsonObject?.getString("campaign_state"),
                            jsonObject?.getString("campaign_address")
                        ))

                    if(jsonArray.length() - 1 == i){
                        Log.i("Campaign Detail", "${campaignList[0].campaign_title} Selected!!!")

                        binding.campaignDetailTitle.text = campaignList[0].campaign_title
                        binding.campaignDetailDate.text = campaignList[0].campaign_date
                        binding.campaignDetailLocation.text = campaignList[0].campaign_state + ", " + campaignList[0].campaign_address
                        binding.campaignDetailTimeStartEnd.text = campaignList[0].campaign_time_start + " - " + campaignList[0].campaign_time_end
                        binding.campaignDetailMaxPeople.text = campaignList[0].max_booking.toString()
                        binding.campaignDetailDescription.text = campaignList[0].campaign_description
                        binding.campaignDetailId.text = campaignList[0].campaign_id.toString()
                    }
                }
            }, Response.ErrorListener { error->
                Log.e("Empty",error.toString().trim { it <= ' ' })
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