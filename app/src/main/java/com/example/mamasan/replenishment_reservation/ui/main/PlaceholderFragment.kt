package com.example.mamasan.replenishment_reservation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.FragmentReplenishmentReservationListBinding
import com.example.mamasan.replenishment_reservation.DataReplenishmentReservation
import com.example.mamasan.replenishment_reservation.OnDetailClicklistener
import com.example.mamasan.replenishment_reservation.ReplenishmentReservationAdapter


import com.google.gson.Gson

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment(), OnDetailClicklistener {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentReplenishmentReservationListBinding? = null

    private lateinit var reservationAdapter : ReplenishmentReservationAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.




    //database


    lateinit var dataReservation: ArrayList<DataReplenishmentReservation>


    var gson = Gson()
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReplenishmentReservationListBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel


        getAllReservation(arguments?.getInt(PlaceholderFragment.ARG_SECTION_NUMBER)?: 1)
        textView.text = pageViewModel.getDataCount().toString()

        return root
    }
    fun getAllReservation(position: Int) {
        var URL = "http://10.0.2.2/mamasan/getReplenishmentReservation.php"
        var statusTab = ""

        statusTab = position.toString()


        dataReservation = ArrayList()
        if (statusTab !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    var replenishment_id: String = ""
                    var replenishment_title: String = ""
                    var replenishment_description: String = ""
                    var replenishment_date_start: String = ""
                    var replenishment_date_end: String = ""
                    var location: String = ""
                    var status: String = ""

                    if (response != "0 results") {
                        val data =
                            gson.fromJson(response, Array<DataReplenishmentReservation>::class.java)
                        pageViewModel.setDataReservation(data)


                        val recyclerView: RecyclerView = _binding!!.RecyclerReservationList
                        reservationAdapter =
                            ReplenishmentReservationAdapter(pageViewModel.dataReservation, this)
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
                    data["status"] = statusTab
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onClicked(position: Int) {
        Toast.makeText(context,"Replenishment"+pageViewModel.dataReservation[position].replenishment_title+" Clicked", Toast.LENGTH_LONG).show()

    }
}