package com.parkinglot.core.dto

import java.time.LocalTime

data class ParkingLotDto(
    val capacity: Int,
    val openHour: LocalTime,
    val closeHour: LocalTime
)
