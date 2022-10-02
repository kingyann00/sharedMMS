package com.example.mamasan.replenishment_reservation

data class DataReplenishmentReservationDetail
    (
    val fullName: String,
    val email: String,
    val contactNo: String,
    val replenishment_title: String,
    val replenishment_description: String,
    val replenishment_date_start: String ,
    val location: String,
    val reservation_dateTime: String,
    val status: String
    ){
}