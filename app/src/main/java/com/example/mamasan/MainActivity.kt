package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.mamasan.databinding.ActivityMainBinding
import com.example.mamasan.replenishment_manage.navigation_replenishment_search
import com.example.mamasan.replenishment_manage.replenishment_list
import com.example.mamasan.replenishment_manage.replenishment_searching
import com.example.mamasan.replenishment_reservation.navigation_replenishment_reservation
import com.example.mamasan.replenishment_reservation.replenishment_reservation_list

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();
        val bundle = Bundle()
        bundle.putInt("id",1)
        val intent = Intent(this,replenishment_list::class.java)
        startActivity(intent)

    }

}