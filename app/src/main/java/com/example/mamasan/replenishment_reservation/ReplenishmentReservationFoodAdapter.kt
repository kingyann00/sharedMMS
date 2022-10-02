package com.example.mamasan.replenishment_reservation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R

class ReplenishmentReservationFoodAdapter (val replenishmentFood: ArrayList<ReplenishmentFood>): RecyclerView.Adapter<ReplenishmentReservationFoodAdapter.ViewHolder>() {
    override fun getItemCount()= replenishmentFood.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
/// completed
        var stok:Int = replenishmentFood[position].stok_quantity- replenishmentFood[position].donate_quantity
        var demand: Int = replenishmentFood[position].demand_quantity
        var donate: Int = replenishmentFood[position].stok_quantity

        holder.foodTittle.text = replenishmentFood[position].foodTittle
        holder.foodType.text = replenishmentFood[position].foodType

        holder.stok_quantity.text = stok.toString()
        holder.donate_quantity.text = replenishmentFood[position].donate_quantity.toString()
        holder.demand_quantity.text = replenishmentFood[position].demand_quantity.toString()

        holder.stok_bar.setProgress(stok)
        holder.stok_bar.setMax(demand)

        holder.donation_bar.setProgress(donate)
        holder.donation_bar.setMax(demand)

        holder.max_donation_bar.isVisible = false
        holder.progressBar.isVisible = false






    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentReservationFoodAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_reservation_food,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodTittle  : TextView = itemView.findViewById(R.id.foodTittle)
        val foodType  : TextView = itemView.findViewById(R.id.foodType)
        val stok_quantity  : TextView = itemView.findViewById(R.id.stok_quantity)
        val donate_quantity  : TextView = itemView.findViewById(R.id.donate_quantity)
        val demand_quantity  : TextView = itemView.findViewById(R.id.demand_quantity)

        val stok_bar : ProgressBar = itemView.findViewById(R.id.stok_bar)
        val donation_bar : ProgressBar = itemView.findViewById(R.id.donation_bar)
        val max_donation_bar : ProgressBar = itemView.findViewById(R.id.max_donation_bar)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)

//        val replenishment_dateTime : TextView = itemView.findViewById(R.id.replenishment_dateTime)

    }
}