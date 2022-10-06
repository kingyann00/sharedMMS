package com.example.mamasan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mamasan.databinding.ActivityProfileDoneeBinding

class ProfileDoneeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileDoneeBinding
    var PREFS_KEY = "prefs"
    var USERID_KEY = "userID"
    var FULLNAME_KEY = "fullName"
    var EMAIL_KEY = "email"
    var ISVERIFIED_KEY = "isVerified"
    var userID = ""
    var fullName = ""
    var email = ""
    var isVerified: Int? = null
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile_donee)

        getUserFromSession()

        binding.tvName.text = fullName
        binding.tvEmail.text = email
        if (isVerified == 1) {
            binding.btnStatus.text = "status: verified"
        } else if (isVerified == 0) {
            binding.btnStatus.text = "status: unverified"
        }else{
            binding.btnStatus.text= "status: error"
        }

        binding.btnProfileDetails.setOnClickListener {
            val intent = Intent(this, ProfileDetailsDoneeActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }

    fun signOut() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        let {
            val intent = Intent(it, SignInActivity::class.java)
            it.startActivity(intent)
            finish()
        }
    }

    fun getUserFromSession() {
        // Get user_id from session
        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        userID = sharedPreferences.getString(USERID_KEY, null)!!
        fullName = sharedPreferences.getString(FULLNAME_KEY, null)!!
        email = sharedPreferences.getString(EMAIL_KEY, null)!!
        isVerified = sharedPreferences.getInt(ISVERIFIED_KEY, 0)
    }

}