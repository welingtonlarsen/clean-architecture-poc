package com.parkinglot.core.usecase

import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class CreateParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotDto: ParkingLotDto): ParkingLot {
        return parkingLotRepository.createParkingLot(parkingLotDto)
            ?: throw Exception("Was not possible create parking lot")
    }
}
