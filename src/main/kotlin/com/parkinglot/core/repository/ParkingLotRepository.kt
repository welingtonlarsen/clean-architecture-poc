package com.parkinglot.core.repository

import arrow.core.Either
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot

interface ParkingLotRepository {
    fun createParkingLot(parkingLotDto: ParkingLotDto): Either<String, ParkingLot>
    fun getParkingLotById(id: Long): Either<String, ParkingLot>
}
