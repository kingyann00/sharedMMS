package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivityMainBinding
import com.example.mamasan.databinding.ActivitySignInBinding
import org.json.JSONObject

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    private val URL: String = "http://10.0.2.2/mamasandb/signin.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        binding.btnSignIn.setOnClickListener { signIn() }
    }

    fun tvSignUpClick(view: View?) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun signIn() {
        var email = etEmail.getText().toString().trim()
        var password = etPassword.getText().toString().trim()

        if (email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        Toast.makeText(
                            this,
                            "Sign in successfully!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SignInActivity, ProfileActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                        finish()
                    } else if (response == "failure") {
                        Toast.makeText(
                            this@SignInActivity,
                            "Invalid Email/Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@SignInActivity,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email
                    data["password"] = password
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }
}