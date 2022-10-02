package com.example.mamasan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import androidx.databinding.DataBindingUtil
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
        setContentView( R.layout.activity_main)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        supportActionBar?.hide();
        val bundle = Bundle()
        bundle.putInt("id",1)

        val intentDonation = Intent(this,DonationActivity::class.java)
        val btnDonation =findViewById<Button>(R.id.buttonDonation)
        btnDonation.setOnClickListener {
            startActivity(intentDonation)

        }

        val btnRl =findViewById<Button>(R.id.btnReplenishment_list)
        val intent = Intent(this,replenishment_list::class.java)
        btnRl.setOnClickListener {
            startActivity(intent)
        }

        val btnRrl = findViewById<Button>(R.id.btnreplenishmentReservationList)
        val intentrrl = Intent(this,replenishment_reservation_list::class.java)
        btnRrl.setOnClickListener {
            startActivity(intentrrl)
        }

        val btnUserModule = findViewById<Button>(R.id.btnUserModule)
        btnUserModule.setOnClickListener {
            startActivity(Intent(this, MamasanMain::class.java))
            finish()
        }

        val btnCCRUD = findViewById<Button>(R.id.buttonCampaingCrud)
        val intentcrud = Intent(this, activity_boonhin::class.java)
        btnCCRUD.setOnClickListener {
            startActivity(intentcrud)
        }

    }



}