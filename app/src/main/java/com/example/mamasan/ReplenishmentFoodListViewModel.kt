package com.example.mamasan

import android.service.controls.ControlsProviderService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ReplenishmentFoodListViewModel : ViewModel() {
    var count = 0
    var foodListViewModel = ArrayList<FoodData>()
    fun updateCount(){
        ++count
    }
    val foodDataLiveData = MutableLiveData<MutableList<FoodData>>()

    init {
        Log.i(ControlsProviderService.TAG,"why u so handsome")
        foodListViewModel.clear()
        foodDataLiveData.value = ArrayList()
    }
    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Showing $it entries"
    }

    fun setIndex(count: Int) {
        _index.value = _index.value?.plus(count)
    }



    fun getFoodData(): LiveData<MutableList<FoodData>> {
        return foodDataLiveData
    }
    fun updateFoodData(
        foodName: String, foodType: String, SKU: String, expiry_Date: String,
        quantity: Int,  skuQuantity: Int){
        val foodData = FoodData(
            foodName,
            foodType,
            SKU,
            expiry_Date,
            quantity,
            skuQuantity,

            )
        foodListViewModel.add(foodData)
    }

//    private fun IntializeFoodData(): MutableList<FoodData>{
//        val  foodData = mutableListOf<FoodData>()
//
//        foodName = arrayOf(
//            "Food 1", "Food 2", "Food 3", "Food 4", "Food 6", "Food 5"
//        )
//
//        foodType = arrayOf(
//            "Frozen Food",
//            "Frozen Food",
//            "Dry Food",
//            "Frozen Food",
//            "Frozen Food",
//            "Frozen Food"
//        )
//
//        quantity = arrayOf(
//            15,20,25,30,25,35
//        )
//        skuQuantity = arrayOf(
//            5,3,35,6,2,1
//        )
//        SKU = arrayOf(
//            "ml","l","box", "kg","l","grams", "kg"
//        )
//
//
//        for (i in foodName.indices) {
//            val data = FoodData(
//                foodName[i],
//                foodType[i],
//                SKU[i],
//                "0",
//                quantity[i],
//                skuQuantity[i],
//            )
//
//            foodListViewModel.add(data)
//
//        }
//        return foodData
//    }

}