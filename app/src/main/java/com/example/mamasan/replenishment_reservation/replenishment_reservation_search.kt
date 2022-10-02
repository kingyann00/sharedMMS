package com.example.mamasan.replenishment_reservation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import com.example.mamasan.databinding.FragmentReplenishmentReservationDetailBinding
import com.example.mamasan.databinding.FragmentReplenishmentReservationSearchBinding
import com.example.mamasan.replenishment_manage.DataReplenishments
import com.example.mamasan.replenishment_manage.ReplenishmentAdapter
import com.example.mamasan.replenishment_manage.replenishment_list
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [replenishment_reservation_search.newInstance] factory method to
 * create an instance of this fragment.
 */
class replenishment_reservation_search : Fragment(),OnDetailClicklistener {
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
    private var _binding: FragmentReplenishmentReservationSearchBinding? = null

    private lateinit var reservationAdapter : ReplenishmentReservationAdapter




    //database


    lateinit var dataReservation: ArrayList<DataReplenishmentReservation>



    private lateinit var viewModel: ViewModel
    var gson = Gson()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReplenishmentReservationSearchBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        _binding!!.header.backIcon.setOnClickListener{
            val intent = Intent(
                activity,
                replenishment_reservation_list::class.java
            )
            startActivity(intent)
        }
        _binding!!.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.

                if (query != null) {
                    getSearchReservation(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                if (newText != null) {
                    getSearchReservation(newText)
                }

                return false
            }
        })
        return  root
    }
    fun getSearchReservation(search: String){
        var URL = "http://10.0.2.2/mamasan/searchReplenishmentReservation.php"
        var condition = ""

        if (search != null)
            condition = search




        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataReplenishmentReservation>::class.java)



                        viewModel.setDataReplenishmentReservation(data)
                        val recyclerView: RecyclerView = _binding!!.RecyclerReplenishmentList
                        reservationAdapter= ReplenishmentReservationAdapter( viewModel.dataReplenishmentReservation,this)
                        val layoutManager = LinearLayoutManager(context)
                        recyclerView.layoutManager = layoutManager

                        recyclerView.adapter = reservationAdapter



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
         * @return A new instance of fragment replenishment_reservation_search.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            replenishment_reservation_search().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClicked(position: Int) {
        val bundle = Bundle()
        bundle.putInt("id",Integer.parseInt(viewModel.dataReplenishmentReservation[position].replenishment_donation_id.toString()))
        findNavController().navigate(R.id.action_replenishment_reservation_search_to_replenishment_reservation_detail, bundle)
    }
}