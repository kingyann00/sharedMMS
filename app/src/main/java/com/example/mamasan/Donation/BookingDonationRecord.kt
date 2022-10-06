package com.example.mamasan.Donation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.mamasan.databinding.FragmentBookingDonationRecordBinding
import com.example.mamasan.databinding.FragmentMoneyDonationRecordBinding
import org.json.JSONObject

class BookingDonationRecord : Fragment() {

    private val URL:String = "http://10.0.2.2/mamasan/view_booking_donation.php"
    private val bookingDateStatusURL:String = "http://10.0.2.2/mamasan/view_bookinglist_date.php"
    private val dateURL:String = "http://10.0.2.2/mamasan/view_booking_date.php"
    var arrayList = ArrayList<DonationBooking>()
    var arlSpinnerDate: MutableList<String?> = ArrayList()
    private lateinit var bookingAdapter: BookingAdapter
    private var date:String? = null
    private var status:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBookingDonationRecordBinding>(
            inflater, R.layout.fragment_booking_donation_record,container,false)

        loadBookingList()
        spinnerBookingDate()
        activity?.let { ArrayAdapter.createFromResource(it, R.array.food_donation_status, android.R.layout.simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerBookingStatus.adapter = adapter
            }

        }
        binding.btnSearchDate.setOnClickListener {
            date = binding.spinnerBookingDate.selectedItem.toString().trim { it <= ' ' }
            status = binding.spinnerBookingStatus.selectedItem.toString().trim { it <= ' ' }
            loadBookingDateStatus()
        }

        binding.btnGomoneyRecord.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_bookingDonationRecord_to_moneyDonationRecord)
        }

        return binding.root
    }

    private fun loadBookingList(){
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
                        DonationBooking(jsonObject?.getString("donor_id"),
                            jsonObject?.getString("status"),
                            jsonObject?.getString("booking_Date"),
                            jsonObject?.getString("booking_Time"),
                            jsonObject?.getString("branch_name"),
                            jsonObject?.getString("location"),
                            jsonObject?.getString("booking_id"),
                            jsonObject?.getString("Food_id"))
                    )

                    if(jsonArray.length() - 1 == i){
                        bookingAdapter = context?.let { BookingAdapter(it,arrayList) }!!
                        val layoutManager = LinearLayoutManager(activity)
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewBooking)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewBooking)?.adapter = bookingAdapter
                        bookingAdapter.notifyDataSetChanged()

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

    private fun loadBookingDateStatus(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, bookingDateStatusURL,
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
                        DonationBooking(jsonObject?.getString("donor_id"),
                            jsonObject?.getString("status"),
                            jsonObject?.getString("booking_Date"),
                            jsonObject?.getString("booking_Time"),
                            jsonObject?.getString("branch_name"),
                            jsonObject?.getString("location"),
                            jsonObject?.getString("booking_id"),
                            jsonObject?.getString("Food_id"))
                    )

                    if(jsonArray.length() - 1 == i){
                        bookingAdapter = context?.let { BookingAdapter(it,arrayList) }!!
                        val layoutManager = LinearLayoutManager(activity)
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewBooking)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewBooking)?.adapter = bookingAdapter
                        bookingAdapter.notifyDataSetChanged()

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
                data["status"] = status!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

    private fun spinnerBookingDate(){
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
                    arlSpinnerDate.add(jsonObject?.getString("booking_Date"))

                    if(jsonArray.length() - 1 == i){
                        val spinnerAdapter =activity?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, arlSpinnerDate
                            )
                        }
                        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        activity?.findViewById<Spinner>(R.id.spinnerBookingDate)?.adapter = spinnerAdapter

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