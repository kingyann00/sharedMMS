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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentBookingDonationRecordBinding
import com.example.mamasan.databinding.FragmentDonationFoodDetailBinding
import kotlinx.android.synthetic.main.fragment_donation_food_detail.*
import org.json.JSONObject

class DonationFoodDetail : Fragment() {

    private val URL:String = "http://10.0.2.2:88/mamasan/view_donation_fooddetail.php"
    private val receivedURL:String = "http://10.0.2.2:88/mamasan/complete_booking_status.php"
    private val cancelURL:String = "http://10.0.2.2:88/mamasan/cancel_booking_status.php"
    var arrayList = ArrayList<FoodList>()
    private lateinit var foodAdapter: FoodListAdapter
    private var foodID:String? = null
    private var bookingID:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDonationFoodDetailBinding>(
            inflater, R.layout.fragment_donation_food_detail,container,false)

        val args = requireArguments()

        foodID = args.getString("food_id")
        bookingID = args.getString("booking_id")

        loadFoodDetailsList()

        binding.btnReceivedFood.setOnClickListener {
            receivedFoodDetailsList()
            view?.findNavController()?.navigate(R.id.action_donationFoodDetail_to_donation)
        }

        binding.btnCancelFood.setOnClickListener {
            cancelFoodDetailsList()
            view?.findNavController()?.navigate(R.id.action_donationFoodDetail_to_donation)

        }



        return binding.root
    }

    private fun loadFoodDetailsList(){
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
                    arrayList.add(FoodList(jsonObject?.getString("Food_id"),
                        jsonObject?.getString("foodName"),
                        jsonObject?.getString("skuQuantity"),
                        jsonObject?.getString("quantity"),
                        jsonObject?.getString("foodType_id"),
                        jsonObject?.getString("description"),
                        jsonObject?.getString("SKU_id"),
                        jsonObject?.getString("symbol")))

                    if(jsonArray.length() - 1 == i){
                        foodAdapter = context?.let { FoodListAdapter(it,arrayList) }!!
                        val layoutManager = LinearLayoutManager(activity)
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewFoodInDonation)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerViewFoodInDonation)?.adapter = foodAdapter
                        foodAdapter.notifyDataSetChanged()

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
                data["food_id"] = foodID!!
                data["booking_id"] = bookingID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

    private fun receivedFoodDetailsList(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, receivedURL,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Donation food received", Toast.LENGTH_SHORT).show()
                } else if (response == "failure") {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show()

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
                data["booking_id"] = bookingID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

    private fun cancelFoodDetailsList(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, cancelURL,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Donation be cancelled", Toast.LENGTH_SHORT).show()
                } else if (response == "failure") {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show()

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
                data["booking_id"] = bookingID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

}