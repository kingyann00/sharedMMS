package com.example.mamasan

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivityProfileDetailsDoneeBinding

class ProfileDetailsDoneeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDetailsDoneeBinding
    private val getInfoURL: String = "http://10.0.2.2/mamasan/get_donee_info.php"
    private val updateInfoURL: String = "http://10.0.2.2/mamasan/update_donee_info.php"

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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile_details_donee)
        getUserFromSession()

        // Initializing shared preferences
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        userID = sharedPreferences.getString(USERID_KEY, null)!!

        getDoneeInfo()

        binding.btnSaveDonorDetails.setOnClickListener {
            updateDoneeInfo()
        }

    }


    fun getUserFromSession() {
        // Get user_id from session
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERID_KEY, null)!!
        fullName = sharedPreferences.getString(FULLNAME_KEY, null)!!
        email = sharedPreferences.getString(EMAIL_KEY, null)!!
    }

    fun getDoneeInfo() {
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, getInfoURL,
            Response.Listener { response ->
                Log.d("res : ", response)
                if (response != "fail") {
                    val profileInfoArray: List<String> = response.split("-")
                    binding.etDoneeDetailsEmail.setText(profileInfoArray[0])
                    binding.etDoneeDetailsFullName.setText(profileInfoArray[1])
                    binding.etDoneeDetailsContactNo.setText(profileInfoArray[2])
                    binding.etDoneeDetailsAddOne.setText(profileInfoArray[3])
                    binding.etDoneeDetailsAddTwo.setText(profileInfoArray[4])
                    binding.etDoneeDetailsPostalCode.setText(profileInfoArray[5])
                    binding.etDoneeDetailsState.setText(profileInfoArray[6])
                    binding.tvFood.setText("Times Get Food Donation: " + profileInfoArray[7].toString())
                    binding.tvMoney.setText("Times Get Money Donation: " + profileInfoArray[8].toString())
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

    fun updateDoneeInfo(){
        val fullName = binding.etDoneeDetailsFullName.text.toString().trim()
        val contactNo = binding.etDoneeDetailsContactNo.text.toString().trim()
        val addressOne = binding.etDoneeDetailsAddOne.text.toString().trim()
        val addressTwo = binding.etDoneeDetailsAddTwo.text.toString().trim()
        val postalCode = binding.etDoneeDetailsPostalCode.text.toString().trim()
        val state = binding.etDoneeDetailsState.text.toString().trim()

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
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(stringRequest)
    }
}