package com.example.mamasan_campaign.CampaignCRUD

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mamasan_campaign.Database.DB_Campaign
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentCampaignLocationCreateBinding

class Fragment_Campaign_Location_Create : Fragment() {
    private lateinit var binding: FragmentCampaignLocationCreateBinding
    private val args: Fragment_Campaign_Location_CreateArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment__campaign__location__create, container,
            false)

        val campaign_title = args.campaign.campaign_title
        val max_booking = args.campaign.max_booking
        val campaign_description = args.campaign.campaign_description
        val campaign_date = args.campaign.campaign_date
        val campaign_time_start = args.campaign.campaign_time_start
        val campaign_time_end = args.campaign.campaign_time_end

        Log.i("Campaign_Title", campaign_title.toString())



        binding.submitButton.setOnClickListener{
            val campaign_state = binding.campaignStateEditText.text.toString()
            val campaign_address = binding.campaignAddressEditText.text.toString()

            val campaign = Campaign(0,campaign_title, campaign_description, max_booking,  campaign_date, campaign_time_start, campaign_time_end,
                campaign_state, campaign_address)
            DB_Campaign().campaignCreate(requireContext(), campaign)

            Toast.makeText(requireContext(), "Successfully Created", Toast.LENGTH_SHORT).show()
            val action = Fragment_Campaign_Location_CreateDirections.actionFragmentCampaignLocationCreateToFragmentCampaignList()
            findNavController().navigate(action)
        }

        return binding.root
    }


}