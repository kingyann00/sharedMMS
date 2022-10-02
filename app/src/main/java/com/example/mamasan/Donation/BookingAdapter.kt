package com.example.mamasan.Donation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mamasan.R
import kotlinx.android.synthetic.main.booking_donation_list.view.*
import kotlinx.android.synthetic.main.booking_donation_list.view.txtBookingDate
import kotlinx.android.synthetic.main.food_list.view.*
import kotlinx.android.synthetic.main.money_donation_list.view.txtDonorID

class BookingAdapter (private val context: Context, private var arrayList: ArrayList<DonationBooking>) :
    RecyclerView.Adapter<BookingAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.booking_donation_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.view.txtDonorID.text = "Donor: " + arrayList?.get(position)?.donorID.toString()
        holder.view.txtStatus.text = "Status: " + arrayList?.get(position)?.booking_status.toString()
        holder.view.txtBookingDate.text = "Booking Date: " + arrayList?.get(position)?.booking_date.toString()
        holder.view.txtBookingTime.text = "Booking Time: " + arrayList?.get(position)?.booking_time.toString()
        holder.view.txtBookingBranch.text = arrayList?.get(position)?.branch.toString()
        holder.view.txtBookingLocation.text = arrayList?.get(position)?.location.toString()
        holder.view.txtBookingID.text = arrayList?.get(position)?.booking_id.toString()
        holder.view.txtBookingFoodID.text = arrayList?.get(position)?.food_id.toString()

        val bundle = Bundle()
        holder.view.cvBooking.setOnClickListener { view:View->
            bundle.putString("booking_id", arrayList?.get(position)?.booking_id.toString())
            bundle.putString("food_id", (arrayList?.get(position)?.food_id.toString()))

            view?.findNavController()?.navigate(R.id.action_bookingDonationRecord_to_donationFoodDetail,bundle)

        }

    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view)
}