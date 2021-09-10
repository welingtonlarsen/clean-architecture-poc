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
    fun createParkingLot(dto: ParkingLotDto): Either<String, ParkingLot> = createParkingLot.execute(dto)

    fun enterCarInParkingLot(parkingLotId: Long, parkedCarDto: ParkedCarDto): Either<String, ParkedCar> =
        enterCarInParkingLot.execute(parkingLotId, parkedCarDto)

    fun getParkingLotById(parkingLotId: Long): Either<String, ParkingLot> = getParkingLot.execute(parkingLotId)
}
