package com.parkinglot.controller

import arrow.core.Either
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.usecase.CreateParkingLot
import com.parkinglot.core.usecase.EnterCarInParkingLot
import com.parkinglot.core.usecase.GetParkingLot
import jakarta.inject.Singleton

@Singleton
class ParkingLotController(
    private val createParkingLot: CreateParkingLot,
    private val enterCarInParkingLot: EnterCarInParkingLot,
    private val getParkingLot: GetParkingLot
) {
    fun createParkingLot(dto: ParkingLotDto): ParkingLot? {
        return try {
            createParkingLot.execute(dto)
        } catch (e: Exception) {
            null
        }
    }

    fun enterCarInParkingLot(parkingLotId: Long, parkedCarDto: ParkedCarDto): Either<String, ParkedCar> {
        return enterCarInParkingLot.execute(parkingLotId, parkedCarDto)
    }

    fun getParkingLotById(parkingLotId: Long): Either<String, ParkingLot> {
        return getParkingLot.execute(parkingLotId)
    }
}
