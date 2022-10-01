package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.mamasan.replenishment_manage.navigation_replenishment_search
import com.example.mamasan.replenishment_manage.replenishment_list
import com.example.mamasan.replenishment_manage.replenishment_searching
import com.example.mamasan.replenishment_reservation.replenishment_reservation_list

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();
//        val intent = Intent(this,navigation_replenishment_search::class.java)
//        startActivity(intent)
    }
}