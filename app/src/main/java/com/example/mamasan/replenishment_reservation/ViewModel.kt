package com.example.mamasan.replenishment_reservation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamasan.replenishment_manage.DataDonation
import com.example.mamasan.replenishment_manage.DataFood
import com.example.mamasan.replenishment_manage.DataReplenishments

class ViewModel : ViewModel()  {
    var dataReplenishmentReservation: ArrayList<DataReplenishmentReservation>
    var replenishmentFood: ArrayList<ReplenishmentFood>

    var dataReplenishmentDonation: ArrayList<DataDonation>
    var dataReplenishmentFood: ArrayList<DataFood>
    var status: Int
    val position = MutableLiveData<Int>()
    val foodIsset = MutableLiveData<Int>()
    val foodId: ArrayList<Int>
    init {

        dataReplenishmentReservation = ArrayList()
        dataReplenishmentDonation = ArrayList()
        dataReplenishmentFood = ArrayList()
        replenishmentFood = ArrayList()
        status = -1
        foodIsset.value = 0
        foodId = ArrayList()
    }

    fun getPosition(click: Int){
        position.value = click
    }
     fun setFoodID(id: Int){
         foodIsset.value = 1
         foodId.add(id)
     }
    fun getDataReplenishmentReservationCount()=dataReplenishmentReservation.size+1
    fun setDataReplenishmentReservation(data: Array<DataReplenishmentReservation>){
        dataReplenishmentReservation.clear()

        for(i in 0..data.size-1){






        val reservation = DataReplenishmentReservation(
                data[i].replenishment_donation_id,
                data[i].fullName,
                data[i].replenishment_title.toString(),
                data[i].location.toString(),
                data[i].reservation_dateTime.toString(),
            )

            dataReplenishmentReservation.add(reservation)


        }

    }
    fun setDataReplenishmentFood(data: Array<ReplenishmentFood>){
        replenishmentFood.clear()

        for(i in 0..data.size-1){


            val food = ReplenishmentFood(
                data[i].foodId,
                data[i].foodTittle,
                data[i].foodType,
                data[i].stok_quantity,
                data[i].donate_quantity,
                data[i].demand_quantity,
            )

            replenishmentFood.add(food)


        }

    }
}