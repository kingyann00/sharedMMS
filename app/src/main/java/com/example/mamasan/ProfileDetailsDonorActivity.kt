package com.example.mamasan

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivityProfileDetailsDonorBinding

class ProfileDetailsDonorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsDonorBinding
    private val getInfoURL: String = "http://10.0.2.2/mamasan/get_donor_info.php"
    private val updateInfoURL: String = "http://10.0.2.2/mamasan/update_donor_info.php"

    var PREFS_KEY = "prefs"
    var USERID_KEY = "userID"
    var FULLNAME_KEY = "fullName"
    var EMAIL_KEY = "email"
    var userID = ""
    var fullName = ""
    var email = ""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_details_donor)
        getUserFromSession()

        // Initializing shared preferences
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        userID = sharedPreferences.getString(USERID_KEY, null)!!

        getDonorInfo()

        binding.btnSaveDonorDetails.setOnClickListener {
            updateDonorInfo()
        }

    }

    fun getUserFromSession() {
        // Get user_id from session
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERID_KEY, null)!!
        fullName = sharedPreferences.getString(FULLNAME_KEY, null)!!
        email = sharedPreferences.getString(EMAIL_KEY, null)!!
    }

    fun getDonorInfo() {
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, getInfoURL,
            Response.Listener { response ->
                Log.d("res : ", response)
                if (response != "fail") {
                    val profileInfoArray: List<String> = response.split("-")
                    binding.etDonorDetailsEmail.setText(profileInfoArray[0])
                    binding.etDonorDetailsFullName.setText(profileInfoArray[1])
                    binding.etDonorDetailsContactNo.setText(profileInfoArray[2])
                    binding.etDonorDetailsAddOne.setText(profileInfoArray[3])
                    binding.etDonorDetailsAddTwo.setText(profileInfoArray[4])
                    binding.etPostalCode.setText(profileInfoArray[5])
                    binding.etDonorDetailsState.setText(profileInfoArray[6])
                    binding.etDonorDetailsBankName.setText(profileInfoArray[7])
                    binding.etDonorDetailsAccName.setText(profileInfoArray[8])
                    binding.etDonorDetailsAccNumber.setText(profileInfoArray[9])
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["userID"] = userID
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }

    fun updateDonorInfo(){
        val fullName = binding.etDonorDetailsFullName.text.toString().trim()
        val contactNo = binding.etDonorDetailsContactNo.text.toString().trim()
        val addressOne = binding.etDonorDetailsAddOne.text.toString().trim()
        val addressTwo = binding.etDonorDetailsAddTwo.text.toString().trim()
        val postalCode = binding.etPostalCode.text.toString().trim()
        val state = binding.etDonorDetailsState.text.toString().trim()
        val bankName = binding.etDonorDetailsBankName.text.toString().trim()
        val accName = binding.etDonorDetailsAccName.text.toString().trim()
        val accNumber = binding.etDonorDetailsAccNumber.text.toString().trim()

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, updateInfoURL,
            Response.Listener { response ->
                Log.d("res : ", response)
                if (response.equals("success")) {
                    Toast.makeText(
                        this,
                        "Profile updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(
                    this,
                    error.toString().trim { it <= ' ' },
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["userID"] = userID
                data["fullName"] = fullName
                data["contactNo"] = contactNo
                data["addressOne"] = addressOne
                data["addressTwo"] = addressTwo
                data["postalCode"] = postalCode
                data["state"] = state
                data["bankName"] = bankName
                data["accName"] = accName
                data["accNumber"] = accNumber
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}