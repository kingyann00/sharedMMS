package com.example.mamasan.replenishment_reservation

data class ReplenishmentFood(
    val foodId : Int,
    val foodTittle: String,
    val foodType: String,
    val stok_quantity: Int,
    val donate_quantity: Int,
    val demand_quantity: Int){

}
