package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.example.mamasan.databinding.ActivityMainBinding
import com.example.mamasan.databinding.ActivityMamasanMainBinding

class MamasanMain : AppCompatActivity() {
    private lateinit var binding: ActivityMamasanMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mamasan_main)
        // get reference to button
        val btnSignInClick = binding.btnMainSignIn
        val btnCreateDonorAcc = binding.btnCreateAcc

        // set on-click listener
        btnSignInClick.setOnClickListener {
            // your code to perform when the user clicks on the button
            startActivity(Intent(this, SignInActivity::class.java))
        }
        btnCreateDonorAcc.setOnClickListener {
            // your code to perform when the user clicks on the button
            startActivity(Intent(this, AccountTypeSelectionActivity::class.java))
        }

    }
}