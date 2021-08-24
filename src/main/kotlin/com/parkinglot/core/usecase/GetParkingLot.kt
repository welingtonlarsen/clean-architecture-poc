package com.parkinglot.core.usecase

import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository

class GetParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotId: Long): ParkingLot {
        return parkingLotRepository.getParkingLotById(parkingLotId) ?: throw Exception("Was not possible to find the parking lot")
    }
}
