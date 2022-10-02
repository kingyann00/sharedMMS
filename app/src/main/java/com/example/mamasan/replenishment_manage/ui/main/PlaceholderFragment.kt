package com.example.mamasan.replenishment_manage.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.example.mamasan.databinding.FragmentReplenishmentListBinding
import com.example.mamasan.replenishment_manage.DataReplenishments
import com.example.mamasan.replenishment_manage.OnDetailClicklistener

import com.example.mamasan.replenishment_manage.ReplenishmentAdapter


import com.google.gson.Gson

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment(), OnDetailClicklistener {

    private lateinit var replenishmentAdapter : ReplenishmentAdapter




    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentReplenishmentListBinding? = null

    //database


    lateinit var dataReplenishments: ArrayList<DataReplenishments>


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

        _binding = FragmentReplenishmentListBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_replenishment_list,container,false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel


        getAllReplenishment(arguments?.getInt(ARG_SECTION_NUMBER)?: 1)
        textView.text = pageViewModel.getDataCount().toString()

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)








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
    fun getAllReplenishment(position: Int) {
        var URL = "http://10.0.2.2/mamasan/getAllReplenishment.php"
        var statusTab = ""

        statusTab = position.toString()


        dataReplenishments = ArrayList()
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


                    val data = gson.fromJson(response,Array<DataReplenishments>::class.java)
                    pageViewModel.setDataReplenishments(data)


                    val recyclerView: RecyclerView = _binding!!.RecyclerReplenishmentList
                    replenishmentAdapter= ReplenishmentAdapter( pageViewModel.dataReplenishments,this)
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager

                    recyclerView.adapter = replenishmentAdapter



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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onClicked(position: Int) {
//        val bundle = Bundle()
//        bundle.putInt("id",1)
//        findNavController().navigate(R.id.action_placeholderFragment_to_replenishment_detail,bundle)


        Toast.makeText(context,"Replenishment"+position+" Clicked", Toast.LENGTH_LONG).show()
    }
}