package com.example.mamasan.replenishment_reservation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R
import com.example.mamasan.replenishment_manage.OnDetailClicklistener
import com.example.mamasan.replenishment_manage.ReplenishmentDonationAdapter

class ReplenishmentFoodAdapter(val replenishmentFood: ArrayList<ReplenishmentFood>,  private val onDetailClicklistener: OnDetailClicklistener):
    RecyclerView.Adapter<ReplenishmentFoodAdapter.ViewHolder>(){
    override fun getItemCount()= replenishmentFood.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
///
        holder.foodTittle.text = replenishmentFood[position].foodTittle
        holder.foodType.text = replenishmentFood[position].foodType
        holder.stok_quantity.text = replenishmentFood[position].stok_quantity.toString()
        holder.donate_quantity.text = replenishmentFood[position].donate_quantity.toString()
        var temp: Int =replenishmentFood[position].stok_quantity + replenishmentFood[position].donate_quantity
        if(temp >  replenishmentFood[position].demand_quantity)
            holder.demand_quantity.setTextColor(Color.parseColor("#D38D92"))
        holder.demand_quantity.text = replenishmentFood[position].demand_quantity.toString()
        holder.stok_bar.setProgress(replenishmentFood[position].stok_quantity)
        holder.stok_bar.setMax(replenishmentFood[position].demand_quantity)
        val current = replenishmentFood[position].stok_quantity+replenishmentFood[position].donate_quantity

        if(replenishmentFood[position].stok_quantity == replenishmentFood[position].demand_quantity) {
            holder.progressBar.setProgress(1)
            holder.progressBar.setMax(1)

        }else if(current > replenishmentFood[position].demand_quantity){
            holder.max_donation_bar.setProgress(1)
            holder.max_donation_bar.setMax(1)

        }else{
            holder.donation_bar.setProgress(replenishmentFood[position].stok_quantity+replenishmentFood[position].donate_quantity)
            holder.donation_bar.setMax(replenishmentFood[position].demand_quantity)
        }
        holder.pending_bar.isVisible = true
        holder.pending_bar.setOnClickListener{
            holder.pending_bar.isVisible = false
            holder.check_bttn.isVisible = true
            onDetailClicklistener.onClicked(position)
        }


//        holder.check_bttn.isVisible = true


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentFoodAdapter.ViewHolder {
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
        val pending_bar : ProgressBar = itemView.findViewById(R.id.pending_bar)
        val check_bttn : ImageView = itemView.findViewById(R.id.check_bttn)

//        val replenishment_dateTime : TextView = itemView.findViewById(R.id.replenishment_dateTime)

    }
}