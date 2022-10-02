package com.example.mamasan.replenishment_manage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mamasan.replenishment_manage.DataReplenishments

class ViewModel : ViewModel() {


    var dataReplenishments: ArrayList<DataReplenishments>
    var dataReplenishmentDonation: ArrayList<DataDonation>
    var dataReplenishmentFood: ArrayList<DataFood>

    val position = MutableLiveData<Int>()

    init {

        dataReplenishments = ArrayList()
        dataReplenishmentDonation = ArrayList()
        dataReplenishmentFood = ArrayList()

    }

    fun getPosition(click: Int){
        position.value = click
    }
    fun getDataReplenishmentCount()=dataReplenishments.size+1
    fun getDataDonationCount()=dataReplenishmentDonation.size+1
    fun setDataReplenishments(data: Array<DataReplenishments>){
        dataReplenishments.clear()

        for(i in 0..data.size-1){


            var replenishment_date_end: String
            if (data[i].replenishment_date_end != null)
                replenishment_date_end =  data[i].replenishment_date_end.toString()
            else
                replenishment_date_end = ""



            val replenishment = DataReplenishments(
                data[i].replenishment_id.toString(),
                data[i].replenishment_title.toString(),
                data[i].replenishment_description.toString(),
                data[i].replenishment_date_start.toString(),
                replenishment_date_end,
                data[i].location,
                data[i].status,
            )

            dataReplenishments.add(replenishment)


        }

    }
    fun setDataDonations(data: Array<DataDonation>){
        dataReplenishmentDonation.clear()

        for(i in 0..data.size-1){





            val donation = DataDonation(
                data[i].userName,
                data[i].dateTime,
                data[i].quantity,

            )

            dataReplenishmentDonation.add(donation)


        }

    }
    fun setDataFoods(data: Array<DataFood>){
        dataReplenishmentFood.clear()

        for(i in 0..data.size-1){





            val food = DataFood(
                data[i].food_id,
                data[i].foodTittle,
                data[i].foodType,
                data[i].stok_quantity,
                data[i].demand_quantity,

                )

            dataReplenishmentFood.add(food)


        }

    }


}