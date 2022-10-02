package com.example.mamasan_campaign.CampaignCRUD

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentCampaignListBinding
import org.json.JSONObject

class Fragment_Campaign_List : Fragment(), CampaignAdapter.OnItemClickListener {

    private lateinit var fragmentBinding: FragmentCampaignListBinding
    private val binding get() = fragmentBinding
    private lateinit var campaignList: ArrayList<CampaignList>
    private lateinit var campaignAdapter: CampaignAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentBinding = FragmentCampaignListBinding.inflate(inflater, container, false)
        retrieveCampaign()

        binding.createCampaignButton.setOnClickListener { view ->
            view.findNavController()
                .navigate(R.id.action_fragment_Campaign_List_to_fragment_Campaign_Information_Create)
        }
        return binding.root
    }

    fun retrieveCampaign() {
        val URLRetrieveCampaign: String = "http://10.0.2.2:8080/mamasan_db_connects/retrieve_campaign.php"
        campaignList = ArrayList()

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLRetrieveCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                campaignList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0) {
                    Log.i("Empty Campaign", "Campaign is Empty")
                }
                for (i in 0 until jsonArray?.length()!!) {
                    val jsonObject = jsonArray?.optJSONObject(i)
                    campaignList.add(
                        CampaignList(
                            jsonObject?.getInt("campaign_id"),
                            jsonObject?.getString("campaign_title"),
                            jsonObject?.getString("campaign_date"),
                            jsonObject?.getString("campaign_timeStart"),
                            jsonObject?.getString("campaign_state")
                        )
                    )
                    if (jsonArray.length() - 1 == i) {
                        campaignAdapter = CampaignAdapter(campaignList, this)
                        binding.adminCampaignList.adapter = campaignAdapter
                        binding.adminCampaignList.layoutManager = LinearLayoutManager(activity)
                        binding.adminCampaignList.setHasFixedSize(true)
                        campaignAdapter.notifyDataSetChanged()
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
//        val campaignTitle = campaignList[position].campaign_title
        val campaignID = campaignList[position].campaign_id.toString().toInt()
//        Log.i("Campaign chosen", "id: $campaignID & name $campaignTitle on click")
        val action = Fragment_Campaign_ListDirections.actionFragmentCampaignListToFragmentCampaignDetail(campaignID)
        findNavController().navigate(action)
    }

}