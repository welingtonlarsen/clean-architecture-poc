package com.parkinglot.controller

import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.usecase.CreateParkingLot
import jakarta.inject.Singleton

@Singleton
class ParkingLotController(private val createParkingLot: CreateParkingLot) {
    fun createParkingLot(dto: ParkingLotDto): ParkingLot? {
        return try {
            createParkingLot.execute(dto)
        } catch (e: Exception) {
            null
        }
    }
}
