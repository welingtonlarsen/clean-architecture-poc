package com.parkinglot.core.repository

import arrow.core.Either
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot

interface ParkingLotRepository {
    fun createParkingLot(parkingLotDto: ParkingLotDto): ParkingLot?
    fun getParkingLotById(id: Long): Either<String, ParkingLot>
    fun saveParkedCar(parkedCarDto: ParkedCarDto, parkingLotId: Long): ParkedCar?
}
