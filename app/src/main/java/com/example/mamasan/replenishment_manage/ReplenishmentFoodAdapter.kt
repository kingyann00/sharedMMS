package com.example.mamasan.replenishment_manage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R

class ReplenishmentFoodAdapter(val dataFood: ArrayList<DataFood>, private val onDetailClicklistener: OnDetailClicklistener):
    RecyclerView.Adapter<ReplenishmentFoodAdapter.ViewHolder>() {
    override fun getItemCount()= dataFood.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.foodTittle.text = dataFood[position].foodTittle
        holder.foodType.text = dataFood[position].foodType
        holder.stok_quantity.text = dataFood[position].stok_quantity.toString()
        holder.demand_quantity.text = dataFood[position].demand_quantity.toString()
        holder.progressBar.setProgress(dataFood[position].stok_quantity)
        holder.progressBar.setMax(dataFood[position].demand_quantity)
        holder.itemView.setOnClickListener{
            onDetailClicklistener.onClicked(position)
                 }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplenishmentFoodAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_replenishment_detail_food,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){



        val foodTittle  : TextView = itemView.findViewById(R.id.foodTittle)
        val foodType : TextView = itemView.findViewById(R.id.foodType)
        val stok_quantity : TextView = itemView.findViewById(R.id.stok_quantity)
        val demand_quantity : TextView = itemView.findViewById(R.id.demand_quantity)
        val progressBar : ProgressBar = itemView.findViewById(R.id.progressBar)


    }
}