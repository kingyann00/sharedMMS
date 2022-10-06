package com.example.mamasan_campaign.CampaignCRUD

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mamasan_campaign.Database.DB_Campaign
import com.example.mamasan.databinding.FragmentCampaignLocationEditBinding

class Fragment_Campaign_Location_Edit : Fragment() {

    private lateinit var fragmentBinding: FragmentCampaignLocationEditBinding
    private val binding get() = fragmentBinding

    private lateinit var campaign: Campaign
    private val args: Fragment_Campaign_Location_CreateArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentCampaignLocationEditBinding.inflate(inflater, container, false)
        val root = binding.root

        val campaign_id = args.campaign.campaign_id.toString()
        val campaign_title = args.campaign.campaign_title
        val max_booking = args.campaign.max_booking
        val campaign_description = args.campaign.campaign_description
        val campaign_date = args.campaign.campaign_date
        val campaign_time_start = args.campaign.campaign_time_start
        val campaign_time_end = args.campaign.campaign_time_end
        val campaign_state = args.campaign.campaign_state
        val campaign_address = args.campaign.campaign_address

        Log.i("Location Edit", campaign_title.toString())

        binding.campaignEditStateEditText.setText(campaign_state)
        binding.campaignEditAddressEditText.setText(campaign_address)


        binding.updateButton.setOnClickListener{
            if(binding.campaignEditStateEditText.text.isBlank() || binding.campaignEditAddressEditText.text.isBlank()){
                //campaign state
                if(binding.campaignEditStateEditText.text.isBlank()){
                    binding.campaignEditStateEditText.error = "This field is required"
                }

                //campaign address
                if(binding.campaignEditAddressEditText.text.isBlank()){
                    binding.campaignEditAddressEditText.error = "This field is required"
                }
            }else{
                val campaign = Campaign(campaign_id.toString().toInt(),campaign_title, campaign_description, max_booking,  campaign_date, campaign_time_start, campaign_time_end,
                    binding.campaignEditStateEditText.text.toString(), binding.campaignEditAddressEditText.text.toString())

                Log.i("Location To DB", campaign.toString())
                Toast.makeText(requireContext(), "Successfully Updated", Toast.LENGTH_SHORT).show()
                DB_Campaign().updateCampaign(requireContext(), campaign)
                val action = Fragment_Campaign_Location_EditDirections.actionFragmentCampaignLocationEditToFragmentCampaignList()
                findNavController().navigate(action)
            }

        }
        return root
    }
}