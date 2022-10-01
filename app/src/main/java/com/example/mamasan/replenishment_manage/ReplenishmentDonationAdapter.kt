package com.example.mamasan.replenishment_manage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R

class ReplenishmentDonationAdapter(val dataDonation: ArrayList<DataDonation>, private val onDetailClicklistener: OnDetailClicklistener):
    RecyclerView.Adapter<ReplenishmentDonationAdapter.ViewHolder>() {

    override fun getItemCount()= dataDonation.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.userName.text = dataDonation[position].userName
        holder.dateTime.text = dataDonation[position].dateTime
        holder.donate_quantity.text = dataDonation[position].quantity.toString()

        holder.itemView.setOnClickListener{
            onDetailClicklistener.onClicked(position)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentDonationAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_replenishment_donation,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val userName  : TextView = itemView.findViewById(R.id.userName)
        val dateTime : TextView = itemView.findViewById(R.id.dateTime)
        val donate_quantity : TextView = itemView.findViewById(R.id.donate_quantity)


    }
}