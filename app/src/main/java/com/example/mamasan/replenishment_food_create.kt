package com.example.mamasan

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.FragmentReplenishmentFoodCreateBinding
import com.google.gson.Gson
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [replenishment_food_create.newInstance] factory method to
 * create an instance of this fragment.
 */
class replenishment_food_create : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private lateinit var viewModel: ReplenishmentFoodListViewModel
    private var FoodList = ArrayList<FoodData>()
    val foodData = ArrayList<FoodData>()

    lateinit var foodName: Array<String>
    lateinit var foodType: Array<String>
    lateinit var SKU: Array<String>
    lateinit var skuQuantity: Array<Int>
    lateinit var quantity: Array<Int>
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var _binding: FragmentReplenishmentFoodCreateBinding
    private val binding get() = _binding

    lateinit var descriptionList: Array<String>
    lateinit var sku_descriptionList: Array<String>
    private var URL: String = ""
    lateinit var foodTypeList: ArrayList<FoodType>
    lateinit var skuList: ArrayList<DataSKU>

    var gson = Gson()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentReplenishmentFoodCreateBinding.inflate(inflater, container, false)
        val root = binding.root

//        dataIntialize()
        viewModel = ViewModelProvider(this).get(ReplenishmentFoodListViewModel::class.java)
//           setFoodList(FoodList)
//        }
//                viewModel.getFoodData().observe(viewLifecycleOwner, Observer {
//
//                    foodData.addAll(it)
//
//
//
//        })
        getFoodType()
        getSKU()
        val recyclerView: RecyclerView = _binding.recyclerFoodList

        FoodList.addAll(viewModel.foodListViewModel)
        val layoutManager = LinearLayoutManager(context)
        foodAdapter= FoodAdapter(FoodList)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = foodAdapter
        _binding.buttonAddOn.setOnClickListener{
            viewModel.updateCount()
var sku: String

            viewModel.updateFoodData(
                _binding.foodNameField.text.toString(),
                _binding.foodTypeSpinner.selectedItem.toString(),
                _binding.skuSpinner.selectedItem.toString(),
                "0",
                Integer.valueOf( _binding.quantityField.text.toString()),
                Integer.valueOf( _binding.skuQuantity.text.toString()),
            )
            _binding.foodNameField.text.clear()
            _binding.quantityField.text.clear()
            _binding.skuQuantity.text.clear()
            FoodList.clear()
            FoodList.addAll(viewModel.foodListViewModel)
            foodAdapter= FoodAdapter(FoodList)


            recyclerView.adapter = foodAdapter
        }

        _binding.replenishmentInformationCard.isVisible = false
        _binding.replenishmentTitleCard.setOnClickListener{
            if (_binding.replenishmentInformationCard.isVisible == true)
                _binding.replenishmentInformationCard.isVisible = false
            else
                _binding.replenishmentInformationCard.isVisible = true
        }
        _binding.foodFormCard.isVisible = false

        _binding.replenishmentFoodCreateCard.setOnClickListener{
            if (_binding.foodFormCard.isVisible == true)
                _binding.foodFormCard.isVisible = false
            else
                _binding.foodFormCard.isVisible = true
        }

        _binding.replenishmentFoodCard.setOnClickListener{
            if (_binding.replenishmentFoodListCard.isVisible == true)
                _binding.replenishmentFoodListCard.isVisible = false
            else
                _binding.replenishmentFoodListCard.isVisible = true
        }

        return root
    }

    fun getFoodType() {
        URL = "http://10.0.2.2/mamasan/foodType.php"
        var temp = ""

        descriptionList = arrayOf("Select A food type")

        if (temp == "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    foodTypeList = ArrayList()
                    var foodType_id: String = ""
                    var description: String = ""
                    val data = gson.fromJson(response,Array<FoodType>::class.java)

                    for(i in 0..data.size-1){


                        foodType_id = data[i].foodType_id.toString()
                        description = data[i].description.toString()

                        val foodTypeData = FoodType(
                            data[i].foodType_id.toString(),
                            data[i].description.toString(),
                        )

                        foodTypeList.add(foodTypeData)
                        descriptionList += foodTypeList[i].description.toString()
                    }


                    _binding.foodTypeSpinner.adapter = ArrayAdapter(requireActivity().applicationContext, R.layout.simple_spinner_item, descriptionList) as SpinnerAdapter

                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["temp"] = temp
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }

    fun storeData() {
        URL = "http://10.0.2.2/mamasan/foodType.php"
        var temp = ""

        descriptionList = arrayOf("Select A food type")

        if (temp == "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    foodTypeList = ArrayList()
                    var foodType_id: String = ""
                    var description: String = ""
                    val data = gson.fromJson(response,Array<FoodType>::class.java)

                    for(i in 0..data.size-1){


                        foodType_id = data[i].foodType_id.toString()
                        description = data[i].description.toString()

                        val foodTypeData = FoodType(
                            data[i].foodType_id.toString(),
                            data[i].description.toString(),
                        )

                        foodTypeList.add(foodTypeData)
                        descriptionList += foodTypeList[i].description.toString()
                    }


                    _binding.foodTypeSpinner.adapter = ArrayAdapter(requireActivity().applicationContext, R.layout.simple_spinner_item, descriptionList) as SpinnerAdapter

                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["temp"] = temp
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }

    fun getSKU(){
        URL = "http://10.0.2.2/mamasan/getSku.php"
        var temp = ""

        sku_descriptionList = arrayOf("Select A SKU")
        skuList = ArrayList()
        if (temp == "") {

            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)

                    var SKU_id: String = ""
                    var description: String = ""
                    var symbol: String = ""
                    val data = gson.fromJson(response,Array<DataSKU>::class.java)

                    for(i in 0..data.size-1){


                        SKU_id = data[i].SKU_id.toString()
                        description = data[i].description.toString()
                        symbol = data[i].symbol.toString()

                        val dataSKU = DataSKU(
                            SKU_id,
                            description,
                            symbol,
                        )

                        skuList.add(dataSKU)

                        sku_descriptionList += description.plus(" (").plus(symbol).plus(")")
                    }


                    _binding.skuSpinner.adapter = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_spinner_item, sku_descriptionList) as SpinnerAdapter

                },
                Response.ErrorListener { error ->

                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["temp"] = temp
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(context)
            requestQueue.add(stringRequest)
        }
    }
    fun updateData(){
        FoodList = ArrayList()

        val foodData = FoodData(
            _binding.foodNameField.text.toString(),
            "ABC FOod",
            "gg",
            "0",
            100,
            500,

            )
        FoodList.add(foodData)


    }
    fun dataIntialize() {


        foodName = arrayOf(
            "Food 1", "Food 2", "Food 3", "Food 4", "Food 6", "Food 5"
        )

        foodType = arrayOf(
            "Frozen Food",
            "Frozen Food",
            "Dry Food",
            "Frozen Food",
            "Frozen Food",
            "Frozen Food"
        )

        quantity = arrayOf(
            15,20,25,30,25,35
        )
        skuQuantity = arrayOf(
            5,3,35,6,2,1
        )
        SKU = arrayOf(
            "ml","l","box", "kg","l","grams", "kg"
        )





        FoodList = ArrayList()
        for (i in foodName.indices) {
            val foodData = FoodData(
                foodName[i],
                foodType[i],
                SKU[i],
                "0",
                quantity[i],
                skuQuantity[i],
            )
            FoodList.add(foodData)

        }



    }

}
