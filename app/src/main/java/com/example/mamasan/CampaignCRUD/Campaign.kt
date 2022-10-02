package com.example.mamasan_campaign.CampaignCRUD

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class CampaignList(
    var campaign_id: Int?, var campaign_title: String?, var campaign_date: String?,
    var campaign_time_start: String?, var campaign_location_state: String?)

data class DonorCampaignList(
    var campaign_id: Int?, var campaign_title: String?, var max_booking: Int?, var campaign_date: String?,
    var campaign_time_start: String?, var campaign_location_state: String?)

@Parcelize
data class Campaign(
    var campaign_id: Int?, var campaign_title: String?, var campaign_description: String?, var max_booking: Int?,
    var campaign_date: String?, var campaign_time_start: String?, var campaign_time_end: String?,
    var campaign_state: String?, var campaign_address: String?):Parcelable

data class DonorCampaign(
    var campaign_id: Int?, var campaign_title: String?, var campaign_description: String?,
    var max_booking: Int?, var participant: Int?,
    var campaign_date: String?, var campaign_time_start: String?, var campaign_time_end: String?,
    var campaign_state: String?, var campaign_address: String?)
