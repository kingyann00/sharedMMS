package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivityCreateDonorBinding

class CreateDonorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateDonorBinding

    private var email: String? = null
    private var password: String? = null
    private var reenterPassword: String? = null
    private var fullName: String? = null
    private var bankName: String? = null
    private var accName: String? = null
    private var accNumber: String? = null
    private var chkBox: Boolean? = null

    lateinit var i: Intent
    private val URL: String = "http://10.0.2.2/mamasan/create-donor.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_donor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val btnSignUpClick = binding.btnSignUpDonor
        btnSignUpClick.setOnClickListener {
            createDonorAccount()
        }
    }

    private fun createDonorAccount() {
        fullName = binding.etFullName.text.toString().trim { it <= ' ' }
        email = binding.etEmail.text.toString().trim { it <= ' ' }
        password = binding.etPassword.text.toString().trim { it <= ' ' }
        reenterPassword = binding.etReenterPassword.text.toString().trim { it <= ' ' }
        bankName = binding.etBankName.text.toString().trim { it <= ' ' }
        accName = binding.etAccName.text.toString().trim { it <= ' ' }
        accNumber = binding.etAccNumber.text.toString().trim { it <= ' ' }
        chkBox = binding.chkboxAgree.isChecked

        if (password != reenterPassword) {
            Toast.makeText(this@CreateDonorActivity, "Password Mismatch", Toast.LENGTH_SHORT).show()
        } else if (chkBox != true) {
            Toast.makeText(this@CreateDonorActivity, "Must agree to our terms & conditions and privacy policy before sign up!", Toast.LENGTH_LONG).show()
        } else if (fullName != "" && email != "" && password != "" && bankName != "" && accName != "" && accNumber != "" && chkBox == true) {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        Toast.makeText(
                            this@CreateDonorActivity,
                            "Successfully create an donor account! Will redirect you to sign in page in 5 seconds.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val handler = Handler()
                        handler.postDelayed({ redirectUserProfile() }, 5000)
                    } else if (response == "failure") {
                        Toast.makeText(
                            this@CreateDonorActivity,
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@CreateDonorActivity,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email!!
                    data["password"] = password!!
                    data["fullName"] = fullName!!
                    data["bankName"] = bankName!!
                    data["accName"] = accName!!
                    data["accNumber"] = accNumber!!
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun tvSignInClick(view: View?) {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun redirectUserProfile() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }
}