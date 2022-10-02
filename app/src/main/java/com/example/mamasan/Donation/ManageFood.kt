package com.example.mamasan.Donation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentManageFoodBinding
import kotlinx.android.synthetic.main.fragment_manage_food.*
import org.json.JSONObject


class ManageFood : Fragment() {
    private val URLSppinerFoodType :String = "http://10.0.2.2:88/mamasan/read_food_type.php"
    private val URLSppinerSkus: String = "http://10.0.2.2:88/mamasan/read_skus.php"
    private val URLAddFood: String = "http://10.0.2.2:88/mamasan/add_food.php"
    private val URLEditFood: String = "http://10.0.2.2:88/mamasan/edit_food.php"
    private val URLDeleteFood: String = "http://10.0.2.2:88/mamasan/delete_food.php"
    private var arlSpinnerFoodType: MutableList<String?> = ArrayList()
    private var arlSpinnerSkus: MutableList<String?> = ArrayList()
    private var foodID:String? = null
    private var foodname:String? = null
    private var foodtype:String? = null
    private var skuQuantity:String? = null
    private var sku:String? = null
    private var unitQuantity:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentManageFoodBinding>(
            inflater, R.layout.fragment_manage_food,container,false)

        spinnerFoodType()
        spinnerSkus()
        val args = requireArguments()

        if (args.getString("mode").equals("edit")){
            binding.addFoodBtn.visibility = View.GONE
            binding.editFoodBtn.visibility = View.VISIBLE
            binding.deleteFoodBtn.visibility = View.VISIBLE
            binding.editFoodName.setText(args.getString("foodName"))
            binding.txtFoodID.text = args.getString("foodID")
            binding.editQuantityUnit.setText(args.getString("quantity"))
            binding.editSkuQuantity.setText(args.getString("skuQuantity"))
            activity?.title = "Manage Food"
        }else if(args.getString("mode").equals("add")){
            activity?.title = "Add Food"
        }



        binding.addFoodBtn.setOnClickListener {
            foodname = binding.editFoodName.text.toString().trim { it <= ' ' }
            foodtype = (binding.spinnerFoodType.selectedItemPosition + 1).toString().trim { it <= ' ' }
            skuQuantity = binding.editSkuQuantity.text.toString().trim { it <= ' ' }
            sku = (binding.spinnerSKU.selectedItemPosition + 1).toString().trim { it <= ' ' }
            unitQuantity = binding.editQuantityUnit.text.toString().trim { it <= ' ' }
            if (binding.editFoodName.text.isEmpty() || binding.editSkuQuantity.text.isEmpty() || binding.editQuantityUnit.text.isEmpty()){
                if (binding.editFoodName.text.isEmpty()){
                    binding.editFoodName.error = "Food name cannot be empty!"
                }
                if (binding.editSkuQuantity.text.isEmpty()){
                    binding.editSkuQuantity.error = "Quantity cannot be empty!"
                }
                if (binding.editQuantityUnit.text.isEmpty()){
                    binding.editQuantityUnit.error = "Sku quantity cannot be empty"
                }
            }else if (unitQuantity.toString().toInt() == 0 || skuQuantity.toString().toInt() == 0){
                if (unitQuantity.toString().toInt() == 0){
                    binding.editQuantityUnit.error = "Quantity cannot be zero!"
                }
                if(skuQuantity.toString().toInt() == 0){
                    binding.editSkuQuantity.error = "Sku quantity cannot be zero!"
                }
            }else{
                view?.findNavController()?.navigate(R.id.action_manageFood_to_foodDonation)
                addFood()
            }

        }

        binding.editFoodBtn.setOnClickListener {
            foodID = binding.txtFoodID.text.toString().trim { it <= ' ' }
            foodname = binding.editFoodName.text.toString().trim { it <= ' ' }
            foodtype = (binding.spinnerFoodType.selectedItemPosition + 1).toString().trim { it <= ' ' }
            skuQuantity = binding.editSkuQuantity.text.toString().trim { it <= ' ' }
            sku = (binding.spinnerSKU.selectedItemPosition + 1).toString().trim { it <= ' ' }
            unitQuantity = binding.editQuantityUnit.text.toString().trim { it <= ' ' }
            if (binding.editFoodName.text.isEmpty() || binding.editSkuQuantity.text.isEmpty() || binding.editQuantityUnit.text.isEmpty()){
                if (binding.editFoodName.text.isEmpty()){
                    binding.editFoodName.error = "Food name cannot be empty!"
                }
                if (binding.editSkuQuantity.text.isEmpty()){
                    binding.editSkuQuantity.error = "Quantity cannot be empty!"
                }
                if (binding.editQuantityUnit.text.isEmpty()){
                    binding.editQuantityUnit.error = "Sku quantity cannot be empty"
                }
            }else if (unitQuantity.toString().toInt() == 0 || skuQuantity.toString().toInt() == 0){
                if (unitQuantity.toString().toInt() == 0){
                    binding.editQuantityUnit.error = "Quantity cannot be zero!"
                }
                if(skuQuantity.toString().toInt() == 0){
                    binding.editSkuQuantity.error = "Sku quantity cannot be zero!"
                }
            }else{
                view?.findNavController()?.navigate(R.id.action_manageFood_to_foodDonation)
                editFood()
            }
        }

        binding.deleteFoodBtn.setOnClickListener {
            foodID = binding.txtFoodID.text.toString().trim { it <= ' ' }
            view?.findNavController()?.navigate(R.id.action_manageFood_to_foodDonation)
            deleteFood()
        }


        return  binding.root
    }


    fun addFood(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLAddFood,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Successfully add a food", Toast.LENGTH_SHORT).show()
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
                data["foodName"] = foodname!!
                data["skuQuantity"] = skuQuantity!!
                data["quantity"] = unitQuantity!!
                data["sku_id"] = sku!!
                data["foodType_id"] = foodtype!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    fun editFood(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLEditFood,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Food edited", Toast.LENGTH_SHORT).show()
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
                data["foodID"] = foodID!!
                data["foodName"] = foodname!!
                data["skuQuantity"] = skuQuantity!!
                data["quantity"] = unitQuantity!!
                data["sku_id"] = sku!!
                data["foodType_id"] = foodtype!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    fun deleteFood(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLDeleteFood,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Toast.makeText(activity, "Food edited", Toast.LENGTH_SHORT).show()
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
                data["foodID"] = foodID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

    private fun spinnerFoodType(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLSppinerFoodType,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arlSpinnerFoodType.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "Food type is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arlSpinnerFoodType.add(jsonObject?.getString("description"))

                    if(jsonArray.length() - 1 == i){
                        val spinAdapter = activity?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, arlSpinnerFoodType
                            )
                        }
                        spinAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        activity?.findViewById<Spinner>(R.id.spinnerFoodType)?.adapter = spinAdapter
                        val args = requireArguments()
                        activity?.findViewById<Spinner>(R.id.spinnerFoodType)?.setSelection(args.getInt("foodTypeID"),true)
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

    private fun spinnerSkus(){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLSppinerSkus,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                arlSpinnerSkus.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Toast.makeText(
                        activity,
                        "Skus is empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    arlSpinnerSkus.add(jsonObject?.getString("symbol"))

                    if(jsonArray.length() - 1 == i){
                        val spinAdapter = activity?.let {
                            ArrayAdapter(
                                it, android.R.layout.simple_spinner_item, arlSpinnerSkus
                            )
                        }
                        spinAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        activity?.findViewById<Spinner>(R.id.spinnerSKU)?.adapter = spinAdapter
                        val args = requireArguments()
                        activity?.findViewById<Spinner>(R.id.spinnerSKU)?.setSelection(args.getInt("sku"),true)
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
}