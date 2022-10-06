package com.example.mamasan_campaign.Database

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mamasan_campaign.CampaignCRUD.Campaign
import org.json.JSONObject

class DB_Campaign(){

    //admin
    private val URLCreateCampaign :String = "http://10.0.2.2:8080/mamasan/create_campaign.php"
    private val URLUpdateCampaign :String = "http://10.0.2.2:8080/mamasan/update_campaign.php"
    private val URLDeleteCampaign :String = "http://10.0.2.2:8080/mamasan/delete_campaign.php"
    private val URLReviewDonee :String = "http://10.0.2.2:8080/mamasan/review_donee.php"
    //donee
    private val URLBookCampaign :String = "http://10.0.2.2:8080/mamasan/book_campaign.php"
    private val URLCancelBookCampaign :String = "http://10.0.2.2:8080/mamasan/cancel_book_campaign.php"

    fun campaignCreate(context: Context, campaign: Campaign){

        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLCreateCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Campaign Successful Inserted")
                } else if (response == "failure") {
                    Log.e("Fail","Campaign Failed Inserted")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_title"] = campaign.campaign_title!!
                data["max_booking"] = campaign.max_booking.toString()!!
                data["campaign_description"] = campaign.campaign_description!!
                data["campaign_date"] = campaign.campaign_date!!
                data["campaign_time_start"] = campaign.campaign_time_start!!
                data["campaign_time_end"] = campaign.campaign_time_end!!
                data["campaign_state"] = campaign.campaign_state!!
                data["campaign_address"] = campaign.campaign_address!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun updateCampaign(context: Context, campaign: Campaign){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLUpdateCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Campaign Successful Updated")
                } else if (response == "failure") {
                    Log.e("Fail","Campaign Failed Updated")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaign.campaign_id.toString()!!
                data["campaign_title"] = campaign.campaign_title!!
                data["max_booking"] = campaign.max_booking.toString()!!
                data["campaign_description"] = campaign.campaign_description!!
                data["campaign_date"] = campaign.campaign_date!!
                data["campaign_time_start"] = campaign.campaign_time_start!!
                data["campaign_time_end"] = campaign.campaign_time_end!!
                data["campaign_state"] = campaign.campaign_state!!
                data["campaign_address"] = campaign.campaign_address!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun deleteCampaign(context: Context, campaignID: String){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLDeleteCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Campaign Successful Deleted")
                } else if (response == "failure") {
                    Log.e("Fail","Campaign Failed Deleted")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun bookCampaign(context: Context, campaignID: String, doneeID: String){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLBookCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Campaign Successful Booked")
                } else if (response == "failure") {
                    Log.e("Fail","Campaign Failed Booked")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                data["donee_id"] = doneeID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun cancelCampaign(context: Context, campaignID: String, doneeID: String){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLCancelBookCampaign,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Campaign Booking Successfully Canceled")
                } else if (response == "failure") {
                    Log.e("Fail","Campaign Failed to Canceled")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["campaign_id"] = campaignID!!
                data["donee_id"] = doneeID!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    fun approveOrReject(context: Context, doneeID: String, campaignID:String, review: String){
        val stringRequest: StringRequest = object : StringRequest(
            Request.Method.POST, URLReviewDonee,
            Response.Listener { response ->
                Log.d("res", response)
                if (response == "success") {
                    Log.i("Success","Success to update review")
                } else if (response == "failure") {
                    Log.e("Fail","Failed to update review")
                }
            },
            Response.ErrorListener { error ->
                Log.e("Error Response", error.toString().trim { it <= ' ' })
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                val data: MutableMap<String, String> = HashMap()
                data["donee_id"] = doneeID!!
                data["campaign_id"] = campaignID!!
                data["review"] = review!!
                return data
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}