package com.example.mamasan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    private val URL: String = "http://10.0.2.2/mamasan/login.php"

    private var email: String? = null
    private var password: String? = null

    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var USERID_KEY = "userID"
    var FULLNAME_KEY = "fullName"
    var EMAIL_KEY = "email"
    var ISVERIFIED_KEY = "isVerified"
    var userID = ""
    var fullName = ""
    var isVerified: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        etEmail = binding.etEmail
        etPassword = binding.etPassword


        // initializing shared preferences
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // clear editor
        editor.clear()
        editor.apply()

        binding.btnSignIn.setOnClickListener {
            email = binding.etEmail.text.toString().trim { it <= ' ' }
            password = binding.etPassword.text.toString().trim { it <= ' ' }
            if (isValidateAll()) {
                val stringRequest: StringRequest = object : StringRequest(
                    Request.Method.POST, URL,
                    Response.Listener { response ->
                        Log.d("res", response)
                        if (response == "failure") {
                            Toast.makeText(
                                this,
                                "Invalid Email/Password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            val infoArray: List<String> = response.split("-")
                            Log.d("user type: ", infoArray[0])
                            //store session for user id using shared preferences
                            var strUserID = infoArray[1]
                            var strFullName = infoArray[2]
                            var strEmail = infoArray[3]

                            userID = sharedPreferences.getString(USERID_KEY, "").toString()
                            fullName = sharedPreferences.getString(FULLNAME_KEY, "").toString()
                            email = sharedPreferences.getString(USERID_KEY, "").toString()

                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString(
                                USERID_KEY,
                                strUserID
                            )
                            editor.putString(
                                FULLNAME_KEY,
                                strFullName
                            )
                            editor.putString(
                                EMAIL_KEY,
                                strEmail
                            )


                            if (infoArray[0] == "donor") {
                                editor.apply()
                                var intent = Intent(this, ProfileDonorActivity::class.java)
                                startActivity(intent)
                            } else if (infoArray[0] == "donee") {
                                var intIsVerified = infoArray[4].toInt()
                                isVerified = sharedPreferences.getInt(ISVERIFIED_KEY, 0)
                                editor.putInt(
                                    ISVERIFIED_KEY,
                                    intIsVerified
                                )
                                editor.apply()
                                var intent = Intent(this, ProfileDoneeActivity::class.java)
                                startActivity(intent)
                            } else {
                                editor.apply()
                                var intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
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
                        data["email"] = email!!
                        data["password"] = password!!
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

    fun tvSignUpClick(view: View?) {
        val intent = Intent(this, AccountTypeSelectionActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun isEmail(textInputField: EditText): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"
        return if (textInputField.text.matches(emailPattern.toRegex())) {
            true
        } else {
            textInputField.error = "Invalid email!"
            false
        }
    }

    fun isEmailEmpty(textInputField: EditText): Boolean {
        return if (textInputField.text.isEmpty()) {
            textInputField.error = "The textfield is required!"
            false
        } else {
            true
        }
    }

    fun isPasswordEmpty(textInputField: EditText): Boolean {
        return if (textInputField.text?.isEmpty() == true) {
            textInputField.error = "The password is required!"
            false
        } else {
            true
        }
    }

    fun isValidateAll(): Boolean {
        return isEmailEmpty(etEmail) && isEmail(etEmail) && isPasswordEmpty(etPassword)
    }
}