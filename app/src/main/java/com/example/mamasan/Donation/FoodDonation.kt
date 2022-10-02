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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentFoodDonationBinding
import org.json.JSONObject

class FoodDonation : Fragment() {
    private val URL:String = "http://10.0.2.2:88/mamasan/read_food_list.php"
    private val cancelURL:String = "http://10.0.2.2:88/mamasan/cancel_donation.php"
    private val completeURL:String = "http://10.0.2.2:88/mamasan/food_donation_done.php"
    var arrayList = ArrayList<FoodList>()
    private lateinit var foodAdapter: FoodListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFoodDonationBinding>(
            inflater,R.layout.fragment_food_donation,container,false
        )

        loadFoodList()

        binding.btnAddFood.setOnClickListener { view:View ->
            val bundle = Bundle()
            bundle.putString("mode","add")
            view.findNavController().navigate(R.id.action_foodDonation_to_manageFood, bundle)
        }

        binding.btnCancelDonation.setOnClickListener { view:View ->
            cancelDonation()
            view.findNavController().navigate(R.id.action_foodDonation_to_donation)
        }
        binding.btnCompleteDonation.setOnClickListener { view:View ->
            if (binding.txtEmptyFood.text.toString().toInt() == 1){
                binding.txtFoodIsEmpty.text = "Cannot donate empty food"
            }else{
                completeDonation()
                view.findNavController().navigate(R.id.action_foodDonation_to_donation)
            }

        }



        return binding.root
    }

    private fun loadFoodList(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URL,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arrayList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    activity?.findViewById<TextView>(R.id.txtFoodIsEmpty)?.text = "List is empty"
                    activity?.findViewById<TextView>(R.id.txtEmptyFood)?.text = "1"
                }else{
                    activity?.findViewById<TextView>(R.id.txtFoodIsEmpty)?.visibility = View.GONE
                    activity?.findViewById<TextView>(R.id.txtEmptyFood)?.text = "2"
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
                        activity?.findViewById<RecyclerView>(R.id.recyclerView)?.layoutManager = layoutManager
                        activity?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter = foodAdapter
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
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)

    }

    fun completeDonation(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, completeURL,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Donation Completed", Toast.LENGTH_SHORT).show()
                } else if (response == "failure") {
                    Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show()

                }
            },
            Response.ErrorListener { error ->
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

    fun cancelDonation(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, cancelURL,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Donation Cancelled", Toast.LENGTH_SHORT).show()
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
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }
}