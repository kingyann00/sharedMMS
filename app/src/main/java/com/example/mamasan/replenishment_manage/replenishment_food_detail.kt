package com.example.mamasan.replenishment_manage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
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
    private  var replenishment_id: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReplenishmentFoodDetailBinding.inflate(inflater, container, false)




        val root = binding.root
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
//        val view = inflater.inflate(R.layout.fragment_replenishment_food_detail,container,false)
        val nameObserver = Observer<Int> { position  ->
            // Update the UI, in this case, a TextView.
            _binding!!.stokLabel.isVisible = false
            _binding!!.stokUnitLabel.isVisible = false
            _binding!!.stokQuantity.isVisible = false
            _binding!!.arrow.isVisible = false
            _binding!!.stokLabel.isVisible = false

            _binding!!.pointerUserName.isVisible = true
            _binding!!.plus.isVisible = true
            _binding!!.donateQuantity.isVisible = true
            _binding!!.unitLabel.isVisible = true
            _binding!!.donationBar.isVisible = true

            _binding!!.pointerUserName.text = viewModel.dataReplenishmentDonation[position].userName
            _binding!!.donateQuantity.text = viewModel.dataReplenishmentDonation[position].quantity.toString()

            _binding!!.donationBar.setProgress(viewModel.dataReplenishmentDonation[position].quantity)
            _binding!!.donationBar.setMax( Integer.parseInt(_binding!!.demandQuantity.text.toString()))
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.position.observe(viewLifecycleOwner, nameObserver)


        _binding!!.header.backIcon.setOnClickListener{
            val bundle = Bundle()
            bundle.putInt("id",replenishment_id)
            findNavController().navigate(R.id.action_replenishment_food_detail_to_replenishment_detail, bundle)
        }
     var id: Int? = arguments?.getInt("id")
        if (id != null) {
            getDonationDetail(id)
            getAllDonation(id);
        };else{
            getDonationDetail(1)
            getAllDonation(1);
        }

       return root
    }
    fun getDonationDetail(foodID: Int){
        var URL = "http://10.0.2.2/mamasan/getReplenishmentFoodDetail.php"
        var condition = ""

        if (foodID != null)
            condition = foodID.toString()
        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataReplenishmentFoodDetail>::class.java)
                        replenishment_id = data[0].replenishment_id
                        _binding!!.foodName.text = data[0].foodName
                        _binding!!.header.headerText.text = data[0].foodName
                        _binding!!.foodType.text = data[0].foodType
                        _binding!!.demandQuantity.text = data[0].demand.toString()
                        var stok = data[0].quantity
                        _binding!!.stokQuantity.text = stok.toString()

                        _binding!!.progressBar.setProgress(stok)
                        _binding!!.progressBar.setMax(data[0].demand)
                        _binding!!.lessQuantity.text = data[0].less.toString()




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
    fun getAllDonation(foodID: Int) {
        var URL = "http://10.0.2.2/mamasan/getReplenishmentDonationFood.php"
        var condition = ""

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

        viewModel.position.setValue(position)
        Toast.makeText(context,viewModel.dataReplenishmentDonation[position].userName+" Clicked", Toast.LENGTH_SHORT).show()
    }
}