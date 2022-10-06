package com.example.mamasan_campaign.DoneeCampaign

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan_campaign.CampaignCRUD.DonorCampaignList
import com.example.mamasan.R

class DoneeCampaignAdapter(
    private val donorCampaignList: ArrayList<DonorCampaignList>,
    private var listener: DoneeCampaignAdapter.OnItemClickListener
): RecyclerView.Adapter<DoneeCampaignAdapter.DonorCampaignViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonorCampaignViewHolder {
        val donorCampaignView: View = LayoutInflater.from(parent.context).inflate(R.layout.donee_campaign_list,parent, false)
        return  DonorCampaignViewHolder(donorCampaignView)
    }

    override fun onBindViewHolder(holder: DonorCampaignViewHolder, position: Int) {
        holder.donor_campaign_id.text = donorCampaignList[position].campaign_id.toString()
        holder.donor_campaign_title.text = donorCampaignList[position].campaign_title
        holder.donor_campaign_maxPeople.text = donorCampaignList[position].max_booking.toString()
        holder.donor_campaign_locationState.text = donorCampaignList[position].campaign_location_state
        holder.donor_campaign_timeStart.text = donorCampaignList[position].campaign_time_start
        holder.donor_campaign_date.text = donorCampaignList[position].campaign_date

        Log.i("onbindviewholder", "$donorCampaignList")
        Log.i("onbindviewholder", "${holder.donor_campaign_title.toString()}")
    }

    override fun getItemCount() = donorCampaignList.size

    inner class DonorCampaignViewHolder(donorCampaignView: View): RecyclerView.ViewHolder(donorCampaignView),
    View.OnClickListener{
        val donor_campaign_id: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_campaign_id)
        val donor_campaign_title: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_title)
        val donor_campaign_maxPeople: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_max_people)
        val donor_campaign_locationState: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_location_state)
        val donor_campaign_timeStart: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_time_start)
        val donor_campaign_date: TextView = donorCampaignView.findViewById(R.id.campaign_donorlist_date)

        init{
            donorCampaignView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            //check position is valid (NO_POSITION = -1)
            if (position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}

