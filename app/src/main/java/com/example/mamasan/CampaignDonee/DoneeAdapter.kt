package com.example.mamasan_campaign.CampaignDonee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan_campaign.CampaignCRUD.CampaignAdapter
import com.example.mamasan.R
import kotlinx.android.synthetic.main.campaign_donee_list.view.*

class DoneeAdapter(
    private val doneeList:ArrayList<Donee>,
    private var listener: DoneeAdapter.OnItemClickListener
    ): RecyclerView.Adapter<DoneeAdapter.DoneeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneeViewHolder {
        val doneeView: View = LayoutInflater.from(parent.context).inflate(R.layout.campaign_donee_list,parent, false)
        return  DoneeViewHolder(doneeView)
    }

    override fun onBindViewHolder(holder: DoneeViewHolder, position: Int) {
        holder.doneeID.text = doneeList[position].donee_id.toString()
        holder.doneeName.text = doneeList[position].donee_name
        holder.doneeEmail.text = doneeList[position].donee_email
        holder.doneeStatus.text = doneeList[position].donee_status
        holder.doneePhone.text = doneeList[position].donee_phone
    }

    override fun getItemCount() = doneeList.size

    inner class DoneeViewHolder(doneeView: View): RecyclerView.ViewHolder(doneeView),
    View.OnClickListener{
        val doneeID: TextView = doneeView.donee_id
        val doneeName: TextView = doneeView.donee_name
        val doneeEmail: TextView = doneeView.donee_email
        val doneeStatus: TextView = doneeView.donee_status
        val doneePhone: TextView = doneeView.donee_phone

        init {
            doneeView.setOnClickListener(this)
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