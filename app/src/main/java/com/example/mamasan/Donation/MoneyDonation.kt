package com.example.mamasan.Donation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentMoneyDonationBinding

class MoneyDonation : Fragment() {

    lateinit var binding: FragmentMoneyDonationBinding
    private var amount: String? = null
    private val URL :String = "http://10.0.2.2/mamasan/insert_money_donation.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMoneyDonationBinding>(
            inflater, R.layout.fragment_money_donation,container,false)


        binding.amountRM50btn.setOnClickListener {
            binding.amountEditTxt.setText("50")
        }
        binding.amountRM100btn.setOnClickListener {
            binding.amountEditTxt.setText("100")
        }
        binding.amountRM200btn.setOnClickListener {
            binding.amountEditTxt.setText("200")
        }
        binding.amountRM500btn.setOnClickListener {
            binding.amountEditTxt.setText("500")
        }
        binding.amountRM1000btn.setOnClickListener {
            binding.amountEditTxt.setText("1000")
        }
        binding.amountRM2000btn.setOnClickListener {
            binding.amountEditTxt.setText("2000")
        }

        binding.donateBtn.setOnClickListener { view:View ->
            amount = binding.amountEditTxt.text.toString().trim { it <= ' ' }
            if (binding.amountEditTxt.text.isEmpty()){
                binding.amountEditTxt.error = "Please enter a number!"
            }else if(binding.amountEditTxt.text.toString().toInt() == 0){
                binding.amountEditTxt.error = "Donation should not be zero!"
            }else{
                view.findNavController().navigate(R.id.action_moneyDonation_to_donateSuccess)
                addMoneyDonation()
            }

        }

        return binding.root
    }

    fun addMoneyDonation(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URL,
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
                data["donation_Amount"] = amount!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }



}