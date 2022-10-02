package com.example.mamasan.Donation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentFirstDonationBinding


class FirstDonation : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFirstDonationBinding>(
            inflater,R.layout.fragment_first_donation,container,false
        )
        binding.btnMoneyDonation.setOnClickListener { view:View -> view.findNavController().navigate(R.id.action_firstDonation_to_moneyDonation) }
        binding.btnFoodDonation.setOnClickListener { view:View -> view.findNavController().navigate(R.id.action_firstDonation_to_bookingDonation) }
        return binding.root
    }


}