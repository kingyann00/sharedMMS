package com.example.mamasan.Donation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R
import kotlinx.android.synthetic.main.food_list.view.*

class DonationFoodListAdapter (private val context: Context, private var arrayList: ArrayList<FoodList>) :
    RecyclerView.Adapter<DonationFoodListAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.food_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.txtFoodIDList.text = arrayList?.get(position)?.foodID.toString()
        holder.view.txtFoodNameList.text = arrayList?.get(position)?.foodName.toString()
        holder.view.txtFoodTypeList.text = arrayList?.get(position)?.foodTypeDes.toString()
        holder.view.txtSkuList.text = arrayList?.get(position)?.skuSymbol.toString()
        holder.view.txtSkuQuantityList.text = arrayList?.get(position)?.skuQuantity.toString()
        holder.view.txtQuantityList.text = arrayList?.get(position)?.quantity.toString()
        holder.view.txtSkuIDList.text = arrayList?.get(position)?.sku.toString()
        holder.view.txtFoodTypeIDList.text = arrayList?.get(position)?.foodTypeID.toString()
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}