package com.example.mamasan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(val FoodList: ArrayList<FoodData>): RecyclerView.Adapter<FoodAdapter.ViewHolder>()  {
    override fun getItemCount()= FoodList.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.foodName.text = FoodList[position].foodName
        holder.foodType.text = FoodList[position].foodType
        holder.SKU.text = FoodList[position].SKU
        holder.skuQuantity.text = FoodList[position].skuQuantity.toString()
        holder.quantity.text = FoodList[position].quantity.toString()









    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_replenishment_food_create,parent, false)
        return  ViewHolder(view)
    }
    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val foodName : TextView = itemView.findViewById(R.id.foodName)
        val foodType  : TextView = itemView.findViewById(R.id.foodType)
        val skuQuantity : TextView = itemView.findViewById(R.id.skuQuantity)
        val SKU  : TextView = itemView.findViewById(R.id.SKU)
        val quantity  : TextView = itemView.findViewById(R.id.quantity)


//        val replenishment_dateTime : TextView = itemView.findViewById(R.id.replenishment_dateTime)

    }
}