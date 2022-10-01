package com.example.mamasan.replenishment_manage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R

class ReplenishmentAdapter
    (val replenishmentList: ArrayList<DataReplenishments>, private  val onDetailClicklistener: OnDetailClicklistener):
    RecyclerView.Adapter<ReplenishmentAdapter.ViewHolder>() {

    override fun getItemCount()= replenishmentList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.replenishmentTitle.text = replenishmentList[position].replenishment_title
        holder.location.text = replenishmentList[position].location
//        holder.replenishment_dateTime.text = campaignList[position].replenishment_dateTime
        holder.itemView.setOnClickListener{
            onDetailClicklistener.onClicked(position)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_replenishment,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val replenishmentTitle  : TextView = itemView.findViewById(R.id.replenishmentTitle)
        val location : TextView = itemView.findViewById(R.id.location)
        val replenishmentDescription : TextView = itemView.findViewById(R.id.replenishmentDescription)
        val replenishment_dateTime : TextView = itemView.findViewById(R.id.replenishment_dateTime)

    }
}