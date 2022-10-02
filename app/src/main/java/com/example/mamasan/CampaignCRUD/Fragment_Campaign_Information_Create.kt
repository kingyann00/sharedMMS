package com.example.mamasan_campaign.CampaignCRUD

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mamasan.databinding.FragmentCampaignInformationCreateBinding
import java.text.SimpleDateFormat
import java.util.*


class Fragment_Campaign_Information_Create : Fragment() {

    private lateinit var fragmentBinding: FragmentCampaignInformationCreateBinding
    private val binding get() = fragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentCampaignInformationCreateBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.campaignTimeStartButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timepicker: TimePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.campaignTimeStartButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        binding.campaignTimeEndButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{timepicker: TimePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.campaignTimeEndButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        binding.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                val Date = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)

                binding.campaignDate.setText(Date)
            })

        binding.continueLocationButton.setOnClickListener{
            val campaign_title = binding.campaignTitleEditText.text.toString()
            val max_booking = binding.campaignMaxPeopleEditText.text.toString().toInt()
            val campaign_description = binding.campaignDescriptionEditText.text.toString()
            val campaign_date = binding.campaignDate.text.toString()
            val campaign_time_start = binding.campaignTimeStartButton.text.toString()
            val campaign_time_end = binding.campaignTimeEndButton.text.toString()

            val campaign = Campaign(0, campaign_title, campaign_description, max_booking, campaign_date, campaign_time_start, campaign_time_end,
            "null", "null")

            val action = Fragment_Campaign_Information_CreateDirections.actionFragmentCampaignInformationCreateToFragmentCampaignLocationCreate(campaign)
            findNavController().navigate(action)
        }

        return root
    }


}