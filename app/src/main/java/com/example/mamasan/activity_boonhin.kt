package com.example.mamasan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.mamasan.databinding.ActivityBoonhinBinding
import com.example.mamasan.databinding.ActivityMainBinding

class activity_boonhin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityBoonhinBinding>(this, R.layout.activity_boonhin)
    }
}