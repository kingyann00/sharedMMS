package com.example.mamasan.replenishment_manage.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mamasan.replenishment_manage.DataReplenishments
import com.google.gson.Gson

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()



    var dataReplenishments: ArrayList<DataReplenishments>


    var gson = Gson()
    init {
        dataReplenishments = ArrayList()
    }

    val text: LiveData<String> = Transformations.map(_index) {
        "Showing $it entries"
    }

    fun getDataCount()= "Showing "+dataReplenishments.size+1+" entries"
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
    fun setIndex(index: Int) {
        _index.value = index
    }


}