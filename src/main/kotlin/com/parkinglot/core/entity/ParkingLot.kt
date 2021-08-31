package com.parkinglot.core.entity

import java.time.LocalTime

data class ParkingLot(
    val id: Long,
    val capacity: Int,
    val openHour: LocalTime,
    val closeHour: LocalTime,
    val parkedCars: List<ParkedCar>
) {
    fun isOpen(date: LocalTime): Boolean = date.isAfter(openHour) && date.isBefore(closeHour)

    fun checkIsFull(): Boolean = parkedCars.size >= capacity
}
