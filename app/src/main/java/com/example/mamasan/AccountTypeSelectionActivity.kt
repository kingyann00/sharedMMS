package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.mamasan.databinding.ActivityAccountTypeSelectionBinding

class AccountTypeSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountTypeSelectionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_type_selection)
    }

    fun createDonorAcc(view: View) {
        startActivity(Intent(this, CreateDonorActivity::class.java))
    }

    fun createDoneeAcc(view: View) {
        startActivity(Intent(this, CreateDoneeActivity::class.java))
    }
}