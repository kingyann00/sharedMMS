package com.example.mamasan.replenishment_reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R

class ReplenishmentReservationAdapter (val reservationList: ArrayList<DataReplenishmentReservation>, private  val onDetailClicklistener: OnDetailClicklistener): RecyclerView.Adapter<ReplenishmentReservationAdapter.ViewHolder>() {

    override fun getItemCount()= reservationList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.fullName.text  = reservationList[position].fullName
        holder.replenishment_title.text = reservationList[position].replenishment_title
        holder.location.text = reservationList[position].location

        holder.reservation_dateTime.text = reservationList[position].reservation_dateTime

        holder.itemView.setOnClickListener{
            onDetailClicklistener.onClicked(position)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentReservationAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_replenishment_reservation,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val fullName : TextView = itemView.findViewById(R.id.fullName)
        val replenishment_title  : TextView = itemView.findViewById(R.id.replenishment_title)
        val location : TextView = itemView.findViewById(R.id.location)
        val reservation_dateTime : TextView = itemView.findViewById(R.id.reservation_dateTime)


    }


}