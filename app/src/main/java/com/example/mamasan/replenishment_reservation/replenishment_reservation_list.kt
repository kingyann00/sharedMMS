package com.example.mamasan.replenishment_reservation

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.mamasan.databinding.ActivityReplenishmentReservationListBinding
import com.example.mamasan.replenishment_reservation.ui.main.SectionsPagerAdapter

class replenishment_reservation_list : AppCompatActivity() {

    private lateinit var binding: ActivityReplenishmentReservationListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReplenishmentReservationListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.header.headerText.text = "Replenishment: Reservation"
        binding.searchBar.setOnClickListener{
            val intent = Intent(this,navigation_replenishment_reservation::class.java)
            startActivity(intent)
        }
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }
}