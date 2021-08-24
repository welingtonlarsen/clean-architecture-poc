package com.parkinglot.core.usecase

import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.repository.ParkingLotRepository

class EnterCarInParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotId: Long, parkedCarDto: ParkedCarDto): ParkedCar {
        val parkingLot = parkingLotRepository.getParkingLotById(parkingLotId)
            ?: throw Exception("Was not possible to find the parking lot")
        if (!parkingLot.isOpen(parkedCarDto.date.toLocalTime())) throw Exception("The parking lot it closed")
        if (parkingLot.isFull()) throw Exception("The parking lot is full")
        val parkedCar = parkingLotRepository.saveParkedCar(parkedCarDto)
            ?: throw Exception("Was not possible to save the parked car")
        parkingLotRepository.increaseParkingLotOccupiedSpace(parkingLotId)
        return parkedCar
    }
}
