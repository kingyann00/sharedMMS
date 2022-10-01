package com.example.mamasan.replenishment_manage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.FragmentReplenishmentSearchingBinding
import com.google.gson.Gson

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [replenishment_searching.newInstance] factory method to
 * create an instance of this fragment.
 */
class replenishment_searching : Fragment(), OnDetailClicklistener  {

    private lateinit var replenishmentAdapter : ReplenishmentAdapter





    private var _binding: FragmentReplenishmentSearchingBinding? = null

    //database


    lateinit var dataReplenishments: ArrayList<DataReplenishments>

    var gson = Gson()
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentReplenishmentSearchingBinding.inflate(inflater, container, false)
        val root = binding.root
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.

                if (query != null) {
                    getAllReplenishment(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                if (newText != null) {
                    getAllReplenishment(newText)
                }

                return false
            }
        })


//        entriesLabel.text = viewModel.getDataCount().toString()

        return root
    }
    fun getAllReplenishment(search: String) {
        var URL = "http://10.0.2.2/mamasan/searchReplenishment.php"
        var condition = ""

        if (search != null)
            condition = search



        dataReplenishments = ArrayList()
        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataReplenishments>::class.java)



                        viewModel.setDataReplenishments(data)
                        val recyclerView: RecyclerView = _binding!!.RecyclerReplenishmentList
                        replenishmentAdapter= ReplenishmentAdapter( viewModel.dataReplenishments,this)
                        val layoutManager = LinearLayoutManager(context)
                        recyclerView.layoutManager = layoutManager

                        recyclerView.adapter = replenishmentAdapter



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


    override fun onClicked(position: Int) {


        Toast.makeText(context,"Replenishment"+viewModel.dataReplenishments[position].replenishment_title+" Clicked", Toast.LENGTH_LONG).show()
    }


}