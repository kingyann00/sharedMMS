package com.example.mamasan_campaign.CampaignCRUD

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R
import kotlinx.android.synthetic.main.fragment__campaign__detail.view.*


class CampaignAdapter(
    private val campaignList: ArrayList<CampaignList>,
    private var listener: OnItemClickListener
): RecyclerView.Adapter<CampaignAdapter.CampaignViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignAdapter.CampaignViewHolder {
        val campaignView: View = LayoutInflater.from(parent.context).inflate(R.layout.admin_campaign_list,parent, false)
        return  CampaignViewHolder(campaignView)
    }

    override fun onBindViewHolder(holder: CampaignViewHolder, position: Int) {
        holder.campaign_id.text = campaignList[position].campaign_id.toString()
        holder.campaign_title.text = campaignList[position].campaign_title
        holder.location_state.text = campaignList[position].campaign_location_state
        holder.campaign_date.text = campaignList[position].campaign_date
        holder.campaign_time.text = campaignList[position].campaign_time_start
    }

    override fun getItemCount()= campaignList.size

    inner class CampaignViewHolder(campaignView: View): RecyclerView.ViewHolder(campaignView),
    View.OnClickListener{
        val campaign_id: TextView = campaignView.findViewById(R.id.campaign_list_campaign_id)
        val campaign_title: TextView = campaignView.findViewById(R.id.campaign_list_title)
        val location_state: TextView = campaignView.findViewById(R.id.campaign_list_location_state)
        val campaign_date: TextView = campaignView.findViewById(R.id.campaign_list_date)
        val campaign_time: TextView = campaignView.findViewById(R.id.campaign_list_time_start)

        init {
            campaignView.setOnClickListener(this)
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