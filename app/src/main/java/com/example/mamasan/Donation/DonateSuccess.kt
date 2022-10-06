package com.example.mamasan.Donation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentDonateSuccessBinding
import org.json.JSONObject
import kotlin.collections.HashMap

class DonateSuccess : Fragment() {

    private val URL :String = "http://10.0.2.2/mamasan/read_money_donation.php"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDonateSuccessBinding>(
            inflater, R.layout.fragment_donate_success,container,false
        )

        moneydonationInfo()

        binding.donationDoneBtn.setOnClickListener { view:View ->
            view.findNavController().navigate(R.id.action_donateSuccess_to_donation)

        }

        return binding.root
    }

    private fun moneydonationInfo(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URL,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                val jsonArray = jsobj?.optJSONArray("result")

                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(0)
                    val time:String? = jsonObject?.getString("donation_time")
                    activity?.findViewById<TextView>(R.id.txtDonationID)?.text = "MD"+jsonObject?.getString("Mdonation_id")
                    activity?.findViewById<TextView>(R.id.txtdonationAmount)?.text = "RM "+jsonObject?.getString("donation_Amount")
                    activity?.findViewById<TextView>(R.id.txtMoneyDonationDate)?.text = jsonObject?.getString("donation_date")
                    activity?.findViewById<TextView>(R.id.txtMoneyDonationTime)?.text = jsonObject?.getString("donation_time")

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