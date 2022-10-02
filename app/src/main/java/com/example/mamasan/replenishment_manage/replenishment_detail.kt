package com.example.mamasan.replenishment_manage

import android.content.Intent
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
import com.example.mamasan.databinding.FragmentReplenishmentDetailBinding
import com.example.mamasan.databinding.FragmentReplenishmentFoodDetailBinding
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [replenishment_detail.newInstance] factory method to
 * create an instance of this fragment.
 */
class replenishment_detail : Fragment(), OnDetailClicklistener {
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

    private lateinit var replenishmentFoodAdapter : ReplenishmentFoodAdapter

    private var _binding: FragmentReplenishmentDetailBinding? = null


    lateinit var dataReplenishment: ArrayList<DataReplenishments>

    var gson = Gson()

    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReplenishmentDetailBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_replenishment_detail,container,false)
        var id: Int? = arguments?.getInt("id")
        if (id != null) {
            getReplenishmentDetail(id)
            getAllFood(id)
        }
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        val root = binding.root
        _binding!!.include.replenishmentTitleCard.setOnClickListener{
            if(_binding!!.include.location.isVisible == true){
                _binding!!.include.locationIcon.isVisible = false
                _binding!!.include.location.isVisible = false
                _binding!!.include.replenishmentDescription.isVisible = false
                _binding!!.include.view2.isVisible = false
            }else{
                _binding!!.include.locationIcon.isVisible = true
                _binding!!.include.location.isVisible = true
                _binding!!.include.replenishmentDescription.isVisible = true
                _binding!!.include.view2.isVisible = true
            }

        }
//        var id: Int = Integer.parseInt(arguments?.getString("id"))

        _binding!!.header.backIcon.setOnClickListener{
            findNavController().navigate(R.id.action_replenishment_detail_to_replenishment_searching)

        }

//_binding!!.header.backIcon.setOnClickListener{
 //findNavController().navigate(R.id.action_replenishment_detail_to_replenishment_searching)
//}
//        val nameObserver = Observer<Int> { position  ->
//            val bundle = Bundle()
//            bundle.putInt("id",viewModel.dataReplenishmentFood[position].food_id)
//            findNavController().navigate(R.id.action_replenishment_detail_to_replenishment_food_detail, bundle)
//
//        }
//

        return root
    }
    fun getReplenishmentDetail(replenishmentID: Int){
        var URL = "http://10.0.2.2/mamasan/getReplenishment.php"
        var condition = ""

        if (replenishmentID != null)
            condition = replenishmentID.toString()
        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataReplenishments>::class.java)



                        _binding!!.header.headerText.text = data[0].replenishment_title
                        _binding!!.include.replenishmentTitle.text = data[0].replenishment_title
                        _binding!!.include.location.text = data[0].location
                        _binding!!.include.replenishmentDescription.text = data[0].replenishment_description
                        _binding!!.include.replenishmentDateTime.text = data[0].replenishment_date_start


//                        _binding!!.include.replenishmentTitle.text =  arguments?.getInt("id").toString()




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
    fun getAllFood(replenishmentID: Int) {
        var URL = "http://10.0.2.2/mamasan/getReplenishmentFoodList.php"
        var condition = ""

        if (replenishmentID != null)
            condition = replenishmentID.toString()



        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){



                        val data = gson.fromJson(response,Array<DataFood>::class.java)



                        viewModel.setDataFoods(data)
                       val recyclerView: RecyclerView = _binding!!.includeFood.recyclerView
                       replenishmentFoodAdapter= ReplenishmentFoodAdapter( viewModel.dataReplenishmentFood, this)
                        val layoutManager = LinearLayoutManager(context)
                        recyclerView.layoutManager = layoutManager

                        recyclerView.adapter = replenishmentFoodAdapter

                        _binding!!.includeFood.TotalFood.text = "Showing "+viewModel.dataReplenishmentFood.size.toString()+" of entries"

                    }else{
                        _binding!!.includeFood.TotalFood.text = "Showing "+viewModel.dataReplenishmentFood.size.toString()+" of entries"
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
         * @return A new instance of fragment replenishment_detail.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            replenishment_detail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClicked(position: Int) {
        viewModel.position.setValue(position)
        val bundle = Bundle()
        bundle.putInt("id",viewModel.dataReplenishmentFood[position].food_id)
        findNavController().navigate(R.id.action_replenishment_detail_to_replenishment_food_detail,bundle)

//        Toast.makeText(context,viewModel.dataReplenishmentFood[position].food_id.toString()+" Clicked", Toast.LENGTH_SHORT).show()
    }
}