package com.example.mamasan.Donation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentBookingDonationBinding
import org.json.JSONObject
import java.time.format.DateTimeFormatter


class BookingDonation : Fragment() {

    private val URLSppiner :String = "http://10.0.2.2/mamasan/read_branch.php"
    private val URLInsertBooking :String = "http://10.0.2.2/mamasan/insert_booking_donation.php"
    private var arlSpinner: MutableList<String?> = ArrayList()
    private var date:String? = null
    private var time:String? = null
    private var venue:String? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentBookingDonationBinding>(
            inflater,R.layout.fragment_booking_donation,container,false
        )

        activity?.let { ArrayAdapter.createFromResource(it, R.array.booking_time, android.R.layout.simple_spinner_item)
            .also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerBookingTime.adapter = adapter
            }

        }

        spinnerBranch()

        binding.calendarBooking.minDate = System.currentTimeMillis()

        binding.calendarBooking.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { calendarView, year, month, day ->
                val bookingDate:String = (year.toString() + "-" + (month + 1) + "-" + day)
                binding.txtBookingDate.text = bookingDate
            }
        )


        binding.bookNowBtn.setOnClickListener { view:View ->
            date = binding.txtBookingDate.text.toString().trim { it <= ' ' }
            time = binding.spinnerBookingTime.selectedItem.toString().trim { it <= ' ' }
            venue = (binding.spinnerBookingBranch.selectedItemPosition + 1).toString().trim { it <= ' ' }
            if (binding.txtBookingDate.text.equals("")){
                binding.BookingDateTxt.error = "Booking Date Cannot be empty"
            }else {
                view.findNavController().navigate(R.id.action_bookingDonation_to_foodDonation)
                addBooking()
            }
        }



        return binding.root
    }

    private fun spinnerBranch(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLSppiner,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arlSpinner.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "Food list is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arlSpinner.add(jsonObject?.getString("branch_name"))

                    if(jsonArray.length() - 1 == i){
                        val spinAdapter = activity?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, arlSpinner
                            )
                        }
                        spinAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        activity?.findViewById<Spinner>(R.id.spinnerBookingBranch)?.adapter = spinAdapter

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

    fun addBooking(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLInsertBooking,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Donation Successfully", Toast.LENGTH_SHORT).show()
                } else if (response == "failure") {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show()

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    getActivity(),
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["booking_Date"] = date!!
                data["booking_Time"] = time!!
                data["branch_id"] = venue!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

}