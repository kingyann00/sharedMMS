package com.example.mamasan_campaign.CampaignCRUD

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mamasan.databinding.FragmentCampaignInformationCreateBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class Fragment_Campaign_Information_Create : Fragment() {

    private lateinit var fragmentBinding: FragmentCampaignInformationCreateBinding
    private val binding get() = fragmentBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding =
            FragmentCampaignInformationCreateBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.campaignTimeStartButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener =
                TimePickerDialog.OnTimeSetListener { timepicker: TimePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    binding.campaignTimeStartButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
                }
            TimePickerDialog(
                activity,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.campaignTimeEndButton.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener =
                TimePickerDialog.OnTimeSetListener { timepicker: TimePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    binding.campaignTimeEndButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
                }
            TimePickerDialog(
                activity,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        binding.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { _, year, month, dayOfMonth ->
                var day: String = ""
                var formatMonth: String = ""
                var adjustMonth = month + 1

                day = if (dayOfMonth < 10) {
                    "0$dayOfMonth"
                } else {
                    "$dayOfMonth"
                }

                formatMonth = if (adjustMonth < 10) {
                    "0$adjustMonth"
                }else {
                    "$adjustMonth"
                }

                val date = ("$year-$formatMonth-$day")

                binding.campaignDate.text = date
            })

        binding.continueLocationButton.setOnClickListener {
            //check blank
            if ((binding.campaignTitleEditText.text.isBlank() || binding.campaignDescriptionEditText.text.isBlank()
                        || binding.campaignMaxPeopleEditText.text.isBlank()
                        || binding.campaignTimeStartButton.text.isBlank() || binding.campaignTimeEndButton.text.isBlank())
                        || binding.campaignDate.text.isBlank()
            ) {
                //campaign title
                if (binding.campaignTitleEditText.text.isBlank()) {
                    binding.campaignTitleEditText.error = "This field is required"
                }

                //max people
                if (binding.campaignMaxPeopleEditText.text.isBlank()) {
                    binding.campaignMaxPeopleEditText.error = "This field is required"
                }

                //description
                if (binding.campaignDescriptionEditText.text.isBlank()) {
                    binding.campaignDescriptionEditText.error = "This field is required"
                }

                //time start button
                if (binding.campaignTimeStartButton.text.isBlank()) {
                    binding.campaignTimeStartButton.error = "This field is required"
                }

                //time end button
                if (binding.campaignTimeEndButton.text.isBlank()) {
                    binding.campaignTimeEndButton.error = "This field is required"
                }

                //calendar
                if (binding.campaignDate.text.isBlank()) {
                    binding.campaignDate.error = "Pick a date!!!"
                }

            //check valid input
            }else if(binding.campaignMaxPeopleEditText.text.toString().toInt() < 1 || !compareDateValidation()){

                if (binding.campaignMaxPeopleEditText.text.toString().toInt() < 1) {
                    binding.campaignMaxPeopleEditText.error = "Less than 1 is invalid"
                }

                if(!compareDateValidation()){
                    binding.campaignDate.error = "Date must be after today!!!"
                }
            //Approved
            }else{
                val campaign_title = binding.campaignTitleEditText.text.toString()
                val max_booking = binding.campaignMaxPeopleEditText.text.toString().toInt()
                val campaign_description = binding.campaignDescriptionEditText.text.toString()
                val campaign_date = binding.campaignDate.text.toString()
                val campaign_time_start = binding.campaignTimeStartButton.text.toString()
                val campaign_time_end = binding.campaignTimeEndButton.text.toString()

                val campaign = Campaign(
                    0,
                    campaign_title,
                    campaign_description,
                    max_booking,
                    campaign_date,
                    campaign_time_start,
                    campaign_time_end,
                    "null",
                    "null"
                )
                val action =
                    Fragment_Campaign_Information_CreateDirections.actionFragmentCampaignInformationCreateToFragmentCampaignLocationCreate(
                        campaign
                    )
                findNavController().navigate(action)
            }
        }

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun compareDateValidation(): Boolean {
        var validatedDate: Boolean = false
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val currentDate: LocalDate = LocalDate.now()

        val currentDateFormat: LocalDate = LocalDate.parse(currentDate.toString(), formatter)
        val campaignDate: LocalDate = LocalDate.parse(binding.campaignDate.text.toString(), formatter)

        when{
            currentDateFormat.isAfter(campaignDate) -> {
                Log.i("invalid", "$currentDateFormat is after $campaignDate")
                validatedDate = false
            }
            currentDateFormat.isBefore(campaignDate) -> {
                Log.i("valid", "$currentDateFormat is before $campaignDate")
                validatedDate = true
            }
        }

        return validatedDate
    }
}