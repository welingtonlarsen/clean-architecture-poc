package com.parkinglot.core.usecase

import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class EnterCarInParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotId: Long, parkedCarDto: ParkedCarDto): com.parkinglot.core.entity.ParkedCar {
        val parkingLot = parkingLotRepository.getParkingLotById(parkingLotId)
            ?: throw Exception("Was not possible to find the parking lot")
        if (!parkingLot.isOpen(parkedCarDto.date.toLocalTime())) throw Exception("The parking lot it closed")
        if (parkingLot.checkIsFull()) throw Exception("The parking lot is full")
        return parkingLotRepository.saveParkedCar(parkedCarDto, parkingLotId)
            ?: throw Exception("Was not possible to save the parked car")
    }
}
