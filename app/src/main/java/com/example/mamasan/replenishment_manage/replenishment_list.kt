package com.example.mamasan.replenishment_manage

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mamasan.MainActivity
import com.example.mamasan.R
import com.example.mamasan.databinding.ActivityReplenishmentListBinding
import com.example.mamasan.replenishment_manage.ui.main.SectionsPagerAdapter


class replenishment_list : AppCompatActivity() {

    private lateinit var binding: ActivityReplenishmentListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide();
        binding = ActivityReplenishmentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        binding.header.headerText.text = "Replenishment: List"
        binding.header.backIcon.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.addBar.setOnClickListener{
            val intent = Intent(this,replenishment::class.java)
            startActivity(intent)
        }
        binding.searchBar.setOnClickListener{
            val intent = Intent(this,navigation_replenishment_search::class.java)
            startActivity(intent)
        }


    }
}