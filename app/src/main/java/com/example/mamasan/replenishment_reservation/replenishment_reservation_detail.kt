package com.example.mamasan.replenishment_reservation

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
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
import com.example.mamasan.replenishment_manage.OnDetailClicklistener
import com.google.gson.Gson
import org.json.JSONArray

class replenishment_reservation_detail : Fragment(), OnDetailClicklistener {

    companion object {
        fun newInstance() = replenishment_reservation_detail()
    }

    private var _binding: FragmentReplenishmentReservationDetailBinding? = null

    private lateinit var reservationAdapter : ReplenishmentReservationFoodAdapter
    private lateinit var foodAdapter : ReplenishmentFoodAdapter
    private lateinit var apprfoodAdapter : ApproveReservationAdapter



    //database


    lateinit var replenishmentFood: ArrayList<ReplenishmentFood>



    private lateinit var viewModel: ViewModel
    var gson = Gson()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReplenishmentReservationDetailBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        val nameObserver = Observer<Int> { foodIsset  ->
            if(foodIsset != 0){
                _binding!!.approveButton.setBackgroundColor(Color.parseColor("#90CC8E"))
                _binding!!.rejectButton.setBackgroundColor(Color.parseColor("#D8D8D8"))
                _binding!!.rejectButton.setTextColor(Integer.parseUnsignedInt("FFFFFFFF",16))

            }

        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.foodIsset.observe(viewLifecycleOwner, nameObserver)
        _binding!!.header.backIcon.setOnClickListener{
            findNavController().navigate(R.id.action_replenishment_reservation_detail_to_replenishment_reservation_search )
        }
        var id: Int? = arguments?.getInt("id")
        if (id != null) {
            getReservationDetail(id)


        }

        return root
    }
    fun getReservationDetail(replenishment_donation_id: Int){
        var URL = "http://10.0.2.2/mamasan/getReplenishmentReservationDetail.php"
        var condition = -1

        if (replenishment_donation_id != null)
            condition = replenishment_donation_id




        if (condition !=  -1) {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){




                        val data = gson.fromJson(response,Array<DataReplenishmentReservationDetail>::class.java)

                        var status: Int = Integer.parseInt(data[0].status)
                        if ( status== 1)
                            _binding!!.header.headerText.text = "Booking"
                        else if (status == 2)
                            _binding!!.header.headerText.text = "Ongoing"
                        else if (status == 3)
                            _binding!!.header.headerText.text = "Completed"

                        _binding!!.userCard.donorName.text = data[0].fullName
                        _binding!!.userCard.email.text = data[0].email
                        var contactNo: String = data[0].contactNo

                        _binding!!.replenishmentCard.replenishmentTitle.text = data[0].replenishment_title
                        _binding!!.replenishmentCard.replenishmentDescription.text = data[0].replenishment_description
                        _binding!!.replenishmentCard.location.text = data[0].location
                        _binding!!.replenishmentCard.replenishmentDateTime.text = data[0].replenishment_date_start

                        _binding!!.reservationDetailCard.fullName.text = data[0].fullName
                        _binding!!.reservationDetailCard.replenishmentTitle.text = data[0].replenishment_title
                        _binding!!.reservationDetailCard.location.text = data[0].location
                        viewModel.status = Integer.parseInt(data[0].status)

                        if (viewModel.status == 3){
                            _binding!!.rejectButton.isVisible = false
                            _binding!!.approveButton.isVisible = false
                        }
                        getAllDonation(condition)

                    }


                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["condition"] = condition.toString()
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    fun getAllDonation(replenishment_donation_id: Int) {
        var URL = "http://10.0.2.2/mamasan/getReplenishmentReservationFood.php"
        var condition = ""

        if (replenishment_donation_id != null)
            condition = replenishment_donation_id.toString()



        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    if (response != "0 results"){



                        val data = gson.fromJson(response,Array<ReplenishmentFood>::class.java)



                        viewModel.setDataReplenishmentFood(data)
//
                  val recyclerView: RecyclerView = _binding!!.replenishmentFoodCard.RecyclerReplenishmentFood

                        if ( viewModel.status == 1 ) {
                            apprfoodAdapter = ApproveReservationAdapter(viewModel.replenishmentFood, this)
                            val layoutManager = LinearLayoutManager(context)
                            recyclerView.layoutManager = layoutManager

                            recyclerView.adapter = apprfoodAdapter
                            _binding!!.rejectButton.text = "Reject"
                            _binding!!.approveButton.text = "Approve"

                            _binding!!.approveButton.setOnClickListener{
                                approveReservation(replenishment_donation_id)

                                val intent = Intent(
                                    activity,
                                    replenishment_reservation_list::class.java
                                )
                                startActivity(intent)
                            }

                            _binding!!.rejectButton.setOnClickListener{
                                approveReservation(id)

                                findNavController().navigate(R.id.action_replenishment_reservation_detail_to_replenishment_reservation_search )


                            }
                        }else if( viewModel.status == 2){
                            foodAdapter = ReplenishmentFoodAdapter(viewModel.replenishmentFood, this)
                            val layoutManager = LinearLayoutManager(context)
                            recyclerView.layoutManager = layoutManager

                            recyclerView.adapter = foodAdapter
                            _binding!!.rejectButton.text = "Cancel"
                            _binding!!.approveButton.text = "Process"

                            _binding!!.approveButton.setOnClickListener{
                                processReservation(replenishment_donation_id)

                                val intent = Intent(
                                    activity,
                                    replenishment_reservation_list::class.java
                                )
                                startActivity(intent)
                            }

                            _binding!!.rejectButton.setOnClickListener{
                                processReservation(id)

                                findNavController().navigate(R.id.action_replenishment_reservation_detail_to_replenishment_reservation_search )


                            }
                        }else if(viewModel.status == 3){
                            reservationAdapter = ReplenishmentReservationFoodAdapter(viewModel.replenishmentFood)
                            val layoutManager = LinearLayoutManager(context)
                            recyclerView.layoutManager = layoutManager

                            recyclerView.adapter = reservationAdapter
                        }

                    }


                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["condition"] = condition.toString()
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    fun approveReservation(replenishment_donation_id: Int) {
        var URL = "http://10.0.2.2/mamasan/approveReplenishmentReservation.php"
        var condition = ""

        if (replenishment_donation_id != null)
            condition = replenishment_donation_id.toString()



        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)




                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["RD"] = condition
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    fun processReservation(replenishment_donation_id: Int) {
        var URL = "http://10.0.2.2/mamasan/processReplenishmentReservation.php"
        var condition = ""

        if (replenishment_donation_id != null)
            condition = replenishment_donation_id.toString()



        if (condition !=  "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)




                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): MutableMap<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["RD"] = condition.toString()
                    var IdString = "";
                    for (i in 0..viewModel.foodId.size-1){
                        IdString = IdString+ viewModel.foodId[i].toString()+"|"

                    }
                    data["arrayFood"] = IdString
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    override fun onClicked(position: Int) {
        viewModel.setFoodID(viewModel.replenishmentFood[position].foodId)

        var IdString = "";
        for (i in 0..viewModel.foodId.size-1){
            IdString = IdString+ viewModel.foodId[i].toString()+"|"

        }
        Toast.makeText(context,IdString+" Clicked", Toast.LENGTH_SHORT).show()
    }


}