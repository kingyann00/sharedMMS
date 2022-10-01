package com.example.mamasan.replenishment_reservation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mamasan.replenishment_reservation.DataReplenishmentReservation
import com.google.gson.Gson

class PageViewModel : ViewModel() {
    var dataReservation: ArrayList<DataReplenishmentReservation>


    var gson = Gson()
    private val _index = MutableLiveData<Int>()

    init {
        dataReservation = ArrayList()
    }
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }
    fun getDataCount()= "Showing "+dataReservation.size+" entries"
    fun setDataReservation(data: Array<DataReplenishmentReservation>){
        dataReservation.clear()

        for(i in 0..data.size-1){




            val replenishment = DataReplenishmentReservation(
                data[i].replenishment_donation_id,
                data[i].fullName.toString(),
                data[i].replenishment_title.toString(),
                data[i].location.toString(),
                data[i].reservation_dateTime.toString(),
            )

            dataReservation.add(replenishment)


        }

    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}