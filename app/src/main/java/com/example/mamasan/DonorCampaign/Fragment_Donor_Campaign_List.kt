package com.example.mamasan_campaign.DonorCampaign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan_campaign.CampaignCRUD.DonorCampaignList
import com.example.mamasan.databinding.FragmentDonorCampaignListBinding
import org.json.JSONObject

class Fragment_Donor_Campaign_List : Fragment(),DonorCampaignAdapter.OnItemClickListener {

    private lateinit var fragmentBinding: FragmentDonorCampaignListBinding
    private val binding get() = fragmentBinding

    private lateinit var donorCampaignList: ArrayList<DonorCampaignList>
    private lateinit var donorCampaignAdapter: DonorCampaignAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentDonorCampaignListBinding.inflate(inflater, container, false)
        retrieveDoneeCampaign()

        return binding.root
    }

    fun retrieveDoneeCampaign() {
        val URLRetrieveCampaign: String = "http://10.0.2.2/mamasan/retrieve_campaign.php"
        donorCampaignList = ArrayList()

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLRetrieveCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                donorCampaignList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0) {
                    Log.i("Empty Campaign", "Campaign is Empty")
                }
                for (i in 0 until jsonArray?.length()!!) {
                    val jsonObject = jsonArray?.optJSONObject(i)
                    donorCampaignList.add(
                        DonorCampaignList(
                            jsonObject?.getInt("campaign_id"),
                            jsonObject?.getString("campaign_title"),
                            jsonObject?.getInt("max_booking"),
                            jsonObject?.getString("campaign_date"),
                            jsonObject?.getString("campaign_timeStart"),
                            jsonObject?.getString("campaign_state")
                        )
                    )
                    if (jsonArray.length() - 1 == i) {
                        donorCampaignAdapter = DonorCampaignAdapter(donorCampaignList, this)
                        binding.donorCampaignList.adapter = donorCampaignAdapter
                        binding.donorCampaignList.layoutManager = LinearLayoutManager(activity)
                        binding.donorCampaignList.setHasFixedSize(true)
                        donorCampaignAdapter.notifyDataSetChanged()
                        Log.i("DonorCampaignList", "$donorCampaignList")
                    }
                }
            }, Response.ErrorListener { error ->
                Log.i("Empty Campaign", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    override fun onItemClick(position: Int) {
        val donorCampaignTitle = donorCampaignList[position].campaign_title
        val campaignID = donorCampaignList[position].campaign_id.toString().toInt()
        Log.i("DCL Chosen", "donor campaign id: ${campaignID.toString()} on click")
        val action = Fragment_Donor_Campaign_ListDirections.actionFragmentDonorCampaignListToFragmentDonorCampaignDetail(campaignID)
        findNavController().navigate(action)
    }
}