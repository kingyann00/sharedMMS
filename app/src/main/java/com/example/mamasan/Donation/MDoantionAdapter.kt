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
import kotlinx.android.synthetic.main.food_list.view.cvFood
import kotlinx.android.synthetic.main.money_donation_list.view.*

class MDoantionAdapter (private val context: Context, private var arrayList: ArrayList<MDonationRecord>) :
    RecyclerView.Adapter<MDoantionAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.money_donation_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.txtDonorID.text = "Donor: " + arrayList?.get(position)?.donor_id.toString()
        holder.view.txtDonationAmount.text = "Donation Amount: " + arrayList?.get(position)?.donation_Amount.toString()
        holder.view.txtDonationDate.text = "Donation Date: " + arrayList?.get(position)?.donation_date.toString()
        holder.view.txtDonationTime.text = "Donation Time: " + arrayList?.get(position)?.donation_Time.toString()
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

}