package com.example.mamasan_campaign.CampaignDonee

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan_campaign.Database.DB_Campaign
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentDoneeListBinding
import org.json.JSONObject
import java.util.HashMap

class Fragment_Donee_List : Fragment(), DoneeAdapter.OnItemClickListener {

    private lateinit var fragmentBinding: FragmentDoneeListBinding
    private val binding get() = fragmentBinding
    private val args: Fragment_Donee_ListArgs by navArgs()


    private lateinit var doneeList: ArrayList<Donee>
    private lateinit var doneeAdapter: DoneeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentDoneeListBinding.inflate(inflater, container, false)

        val campaignID = args.campaignID.toString()
        retrieveDonee(campaignID)

        var donorStatus = resources.getStringArray(R.array.donorstatus)
        binding.spinnerFilterDonee.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, donorStatus)
        binding.spinnerFilterDonee.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, Id: Long) {
//                spinnerSearch(campaignID, donorStatus[position])
//                doneeAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
//                doneeAdapter.notifyDataSetChanged()
            }
        }

       return binding.root
    }

    override fun onItemClick(position: Int) {
        val campaignID = args.campaignID.toString()
        val doneeID = doneeList[position].donee_id.toString().toInt()
        Log.i("Donee chosen", "id: $doneeID on click, $campaignID")

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Approve or Reject ${ doneeList[position].donee_name.toString()} ?")
        builder.setTitle("Invite to Campaign ?")
        builder.setCancelable(true)

        builder.setPositiveButton("Approve") {
                dialog, which ->
            DB_Campaign().approveOrReject(requireContext(), doneeID.toString(), campaignID, "Approved")
        }

        builder.setNegativeButton("Reject") {
                dialog, which ->
            DB_Campaign().approveOrReject(requireContext(), doneeID.toString(), campaignID, "Rejected")
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun retrieveDonee(campaignID: String){
        doneeList = java.util.ArrayList()
        val URLRetrieveDonee: String = "http://10.0.2.2:8080/mamasan/retrieve_donee.php"

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLRetrieveDonee,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                doneeList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Log.i("Empty","Donee Not Found")
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    doneeList.add(
                        Donee(
                            jsonObject?.getInt("doneeID"),
                            jsonObject?.getString("fullName"),
                            jsonObject?.getString("email"),
                            jsonObject?.getString("status"),
                            jsonObject?.getString("contactNo")
                        )
                    )
                    if(jsonArray.length() - 1 == i){
                        doneeAdapter = DoneeAdapter(doneeList, this)
                        binding.campaignDoneeList.adapter = doneeAdapter
                        binding.campaignDoneeList.layoutManager = LinearLayoutManager(activity)
                        binding.campaignDoneeList.setHasFixedSize(true)
                        doneeAdapter.notifyDataSetChanged()
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

    fun spinnerSearch(campaignID: String, spinnerValue: String){
        val URLSpinnerRetrieveDonee: String = "http://10.0.2.2:8080/mamasan/spinner_retrieve_donee.php"
        doneeList = ArrayList()
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLSpinnerRetrieveDonee,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                doneeList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Log.i("Empty","Donee Not Found")
                    binding.emptyTextView.text = "Donee Not Found"
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    doneeList.add(
                        Donee(
                            jsonObject?.getInt("doneeID"),
                            jsonObject?.getString("fullName"),
                            jsonObject?.getString("email"),
                            jsonObject?.getString("status"),
                            jsonObject?.getString("contactNo")
                        )
                    )
                    if(jsonArray.length() - 1 == i){
                        doneeAdapter = DoneeAdapter(doneeList, this)
                        binding.campaignDoneeList.adapter = doneeAdapter
                        binding.campaignDoneeList.layoutManager = LinearLayoutManager(activity)
                        binding.campaignDoneeList.setHasFixedSize(true)
                        doneeAdapter.notifyDataSetChanged()
                    }
                }
            }, Response.ErrorListener { error->
                Log.e("Empty",error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                data["status"] = spinnerValue!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

}