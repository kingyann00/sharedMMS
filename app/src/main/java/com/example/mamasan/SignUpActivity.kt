package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivityMainBinding
import com.example.mamasan.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private var email: String? = null
    private var password: String? = null
    private var reenterPassword: String? = null
    private var fullName: String? = null
    private var contactNo: String? = null
    private var addressOne: String? = null
    private var addressTwo: String? = null
    private var postalCode: String? = null
    private var state: String? = null
    private var userImagePath: String? = null
    private var status: Int? = null

    lateinit var i: Intent
    private val URL: String = "http://10.0.2.2/mamasan/signup.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val btnSignUpClick = binding.btnSignUp
        btnSignUpClick.setOnClickListener {
            createNewAccount()
        }
    }

    fun createNewAccount() {
        fullName = binding.etFullName.text.toString().trim { it <= ' ' }
        email = binding.etEmail.text.toString().trim { it <= ' ' }
        password = binding.etPassword.text.toString().trim { it <= ' ' }
        reenterPassword = binding.etReenterPassword.text.toString().trim { it <= ' ' }

        if (password != reenterPassword) {
            Toast.makeText(this@SignUpActivity, "Password Mismatch", Toast.LENGTH_SHORT).show()
        } else if (fullName != "" && email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Successfully create an account! Will redirect you to sign in page in 3 seconds.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val handler = Handler()
                        handler.postDelayed({ redirectUserProfile() }, 3000)
                    } else if (response == "failure") {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Something went wrong!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@SignUpActivity,
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
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        }else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }

    fun tvSignInClick(view: View?){
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