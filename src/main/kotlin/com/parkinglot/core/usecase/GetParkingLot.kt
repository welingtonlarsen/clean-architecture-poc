package com.parkinglot.core.usecase

import arrow.core.Either
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class GetParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotId: Long): Either<String, ParkingLot> {
        return parkingLotRepository.getParkingLotById(parkingLotId)
    }
}
