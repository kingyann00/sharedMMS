package com.example.mamasan_campaign.CampaignCRUD

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan.R
import com.example.mamasan.databinding.FragmentCampaignInformationEditBinding
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Fragment_Campaign_Information_Edit : Fragment() {

    private lateinit var fragmentBinding: FragmentCampaignInformationEditBinding
    private val binding get() = fragmentBinding
    private val args: Fragment_Campaign_DetailArgs by navArgs()

    private lateinit var campaignList: ArrayList<Campaign>
    private val URLCampaignDetail:String = "http://10.0.2.2:8080/mamasan_db_connects/campaign_detail.php"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentBinding = FragmentCampaignInformationEditBinding.inflate(inflater, container, false)
        val root = binding.root

        val campaign_id = args.campaignId
        Log.i("Campaign_InfoEdit", "${campaign_id.toString()}")

        searchCampaign(campaign_id.toString())
        binding.campaignEditTimeStartButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timepicker: TimePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.campaignEditTimeStartButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE),true).show()
        }

        binding.campaignEditTimeEndButton.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timepicker: TimePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                binding.campaignEditTimeEndButton.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }
            TimePickerDialog(activity,timeSetListener,cal.get(Calendar.HOUR_OF_DAY), cal.get(
                Calendar.MINUTE),true).show()
        }

        binding.calendarView.setOnDateChangeListener(
            CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
                val Date = (dayOfMonth.toString() + "-"
                        + (month + 1) + "-" + year)
                binding.campaignEditDate.setText(Date)
            })

        binding.continueEditLocationButton.setOnClickListener{

            val campaign_title = binding.campaignEditTitleEditText.text.toString()
            val max_booking = binding.campaignEditMaxPeopleEditText.text.toString().toInt()
            val campaign_description = binding.campaignEditDescriptionEditText.text.toString()
            val campaign_date = binding.campaignEditDate.text.toString()
            val campaign_time_start = binding.campaignEditTimeStartButton.text.toString()
            val campaign_time_end = binding.campaignEditTimeEndButton.text.toString()
            val campaign_locationState = binding.campaignEditLocationState.text.toString()
            val campaign_locationAddress = binding.campaignEditLocationAddress.text.toString()

            val campaign = Campaign(campaign_id, campaign_title, campaign_description, max_booking, campaign_date, campaign_time_start, campaign_time_end,
                campaign_locationState, campaign_locationAddress)
            val action = Fragment_Campaign_Information_EditDirections.actionFragmentCampaignInformationEditToFragmentCampaignLocationEdit(campaign)
            findNavController().navigate(action)
        }
        return binding.root
    }

    fun searchCampaign(campaignID: String){
        campaignList = ArrayList()

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLCampaignDetail,
            Response.Listener { response ->
                Log.d("res", response)
                var jsobj: JSONObject = JSONObject(response)
                campaignList.clear()
                val jsonArray = jsobj?.optJSONArray("result")
                if (jsonArray?.length() == 0){
                    Log.i("Empty","Campaign Not Found")
                }
                for (i in 0 until jsonArray?.length()!!){
                    val jsonObject = jsonArray?.optJSONObject(i)
                    campaignList.add(
                        Campaign(
                            jsonObject?.getInt("campaign_id"),
                            jsonObject?.getString("campaign_title"),
                            jsonObject?.getString("campaign_description"),
                            jsonObject?.getInt("max_booking"),
                            jsonObject?.getString("campaign_date"),
                            jsonObject?.getString("campaign_timeStart"),
                            jsonObject?.getString("campaign_timeEnd"),
                            jsonObject?.getString("campaign_state"),
                            jsonObject?.getString("campaign_address")
                        ))

                    if(jsonArray.length() - 1 == i){
                        Log.i("Campaign Detail", "${campaignList[0].campaign_title} Selected!!!")
                        binding.campaignEditTitleEditText.setText(campaignList[0].campaign_title)
                        binding.campaignEditMaxPeopleEditText.setText(campaignList[0].max_booking.toString())
                        binding.campaignEditDescriptionEditText.setText(campaignList[0].campaign_description)
                        binding.campaignEditTimeStartButton.text = campaignList[0].campaign_time_start
                        binding.campaignEditTimeEndButton.text = campaignList[0].campaign_time_end
                        binding.campaignEditDate.text = campaignList[0].campaign_date
                        binding.campaignEditLocationState.text = campaignList[0].campaign_state
                        binding.campaignEditLocationAddress.text = campaignList[0].campaign_address
                    }
                }
            }, Response.ErrorListener { error->
                Log.e("Empty",error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(activity)
        requestQueue.add(stringRequest)
    }

}