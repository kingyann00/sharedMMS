package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.mamasan.databinding.ActivityProfileBinding
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    var arrayList = ArrayList<Users>()
    var fullName: String = "Hon King Yann"
    private val URL: String = "http://10.0.2.2/mamasan/get_users_details.php"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        val email = intent.getStringExtra("email")
        if (email != null) {
            binding.tvEmail.text = email
            if (fullName != null) {
                binding.tvName.text = fullName
            }
        }
        binding.btnProfileDetails.setOnClickListener{
            val intent = Intent(this, ProfileDetailsActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

}