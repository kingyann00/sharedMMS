package com.example.mamasan.Donation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentMoneyDonationBinding
import com.example.mamasan.databinding.FragmentMoneyDonationRecordBinding
import kotlinx.android.synthetic.main.fragment_money_donation_record.*
import org.json.JSONObject

class MoneyDonationRecord : Fragment() {
    private val URL:String = "http://10.0.2.2:88/mamasan/view_money_donation.php"
    private val mdonationDateURL:String = "http://10.0.2.2:88/mamasan/view_moneylist_date.php"
    private val dateURL:String = "http://10.0.2.2:88/mamasan/view_money_date.php"
    var arrayList = ArrayList<MDonationRecord>()
    var arlSpinnerDate: MutableList<String?> = ArrayList()
    private lateinit var mdonationAdapter: MDoantionAdapter
    private var date:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMoneyDonationRecordBinding>(
            inflater, R.layout.fragment_money_donation_record,container,false)

        loadMoneyList()
        spinnerMoneyDate()
        binding.btnSearchDate.setOnClickListener {
            date = binding.spinnerMdoantionDate.selectedItem.toString().trim { it <= ' ' }
            loadMoneyDate()
        }

        binding.btnGoFoodRecord.setOnClickListener { view:View ->
            view?.findNavController()?.navigate(R.id.action_moneyDonationRecord_to_bookingDonationRecord)
        }


        return binding.root
    }

    private fun loadMoneyList(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URL,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arrayList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "No any record here",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arrayList.add(
                        MDonationRecord(jsonObject?.getString("donor_id"),
                        jsonObject?.getString("donation_Amount"),
                        jsonObject?.getString("donation_date"),
                        jsonObject?.getString("donation_time"))
                    )

                    if(jsonArray.length() - 1 == i){
                        mdonationAdapter = context?.let { MDoantionAdapter(it,arrayList) }!!
                        val layoutManager = LinearLayoutManager(activity)
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewMdonation)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewMdonation)?.adapter = mdonationAdapter
                        mdonationAdapter.notifyDataSetChanged()

                    }
                }
            }, Response.ErrorListener { error->
                Toast.makeText(
                    activity,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
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

    private fun loadMoneyDate(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, mdonationDateURL,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arrayList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "No any record here",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arrayList.add(
                        MDonationRecord(jsonObject?.getString("donor_id"),
                            jsonObject?.getString("donation_Amount"),
                            jsonObject?.getString("donation_date"),
                            jsonObject?.getString("donation_time"))
                    )

                    if(jsonArray.length() - 1 == i){
                        mdonationAdapter = context?.let { MDoantionAdapter(it,arrayList) }!!
                        val layoutManager = LinearLayoutManager(activity)
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewMdonation)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewMdonation)?.adapter = mdonationAdapter
                        mdonationAdapter.notifyDataSetChanged()

                    }
                }
            }, Response.ErrorListener { error->
                Toast.makeText(
                    activity,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["donation_date"] = date!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

    private fun spinnerMoneyDate(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, dateURL,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arlSpinnerDate.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "No any record here",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arlSpinnerDate.add(jsonObject?.getString("donation_date"))

                    if(jsonArray.length() - 1 == i){
                        val spinnerAdapter =activity?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, arlSpinnerDate
                            )
                        }
                        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        activity?.findViewById<Spinner>(R.id.spinnerMdoantionDate)?.adapter = spinnerAdapter

                    }
                }
            }, Response.ErrorListener { error->
                Toast.makeText(
                    activity,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
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
}

