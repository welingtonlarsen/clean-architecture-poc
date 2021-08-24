package com.parkinglot.infra.database.memory.model

import java.time.LocalTime

class ParkingLotModel(
    val id: Long,
    val capacity: Int,
    val occupiedSpaces: Int,
    val openHour: LocalTime,
    val closeHour: LocalTime
)
