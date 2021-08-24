package com.parkinglot.core.entity

import java.time.LocalTime

data class ParkingLot(
    val id: Long,
    val capacity: Int,
    val occupiedSpaces: Int,
    val openHour: LocalTime,
    val closeHour: LocalTime
) {
    fun isOpen(date: LocalTime): Boolean = date.isAfter(openHour) && date.isBefore(closeHour)

    fun isFull(): Boolean = occupiedSpaces == capacity
}
