package com.parkinglot.core.usecase

import arrow.core.Either
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class CreateParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotDto: ParkingLotDto): Either<String, ParkingLot> {
        return parkingLotRepository.createParkingLot(parkingLotDto)
    }
}
