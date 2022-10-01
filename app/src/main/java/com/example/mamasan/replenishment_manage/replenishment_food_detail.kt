package com.example.mamasan.replenishment_manage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.FragmentReplenishmentFoodDetailBinding
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [replenishment_food_detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class replenishment_food_detail : Fragment(), OnDetailClicklistener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    //donationAdapter
    private lateinit var replenishmentDonationAdapter : ReplenishmentDonationAdapter





    private var _binding: FragmentReplenishmentFoodDetailBinding? = null

    //database


    lateinit var dataDonation: ArrayList<DataDonation>

    var gson = Gson()
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReplenishmentFoodDetailBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        getAllDonation(1);
       return root
    }
    fun getAllDonation(foodID: Int) {
        var URL = "http://10.0.2.2/mamasan/getReplenishmentDonationFood.php"
        var condition = "1"

        if (foodID != null)
            condition = foodID.toString()



        dataDonation = ArrayList()
        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataDonation>::class.java)



                        viewModel.setDataDonations(data)
                        val recyclerView: RecyclerView = _binding!!.RecylerDonation
                        replenishmentDonationAdapter= ReplenishmentDonationAdapter( viewModel.dataReplenishmentDonation, this)
                        val layoutManager = LinearLayoutManager(context)
                        recyclerView.layoutManager = layoutManager

                        recyclerView.adapter = replenishmentDonationAdapter



                    }


                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["condition"] = condition
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment replenishment_food_detail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            replenishment_food_detail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClicked(position: Int) {
        Toast.makeText(context,"Replenishment"+viewModel.dataReplenishmentDonation[position].userName+" Clicked", Toast.LENGTH_LONG).show()
    }
}