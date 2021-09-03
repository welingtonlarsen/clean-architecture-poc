package com.parkinglot.core.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class EnterCarInParkingLot(private val parkingLotRepository: ParkingLotRepository) {
    fun execute(parkingLotId: Long, parkedCarDto: ParkedCarDto): Either<String, ParkedCar> {
        return when (val parkingLot = parkingLotRepository.getParkingLotById(parkingLotId)) {
            is Either.Left -> {
                Either.Left(parkingLot.value)
            }
            is Either.Right -> {
                if (!parkingLot.value.isOpen(parkedCarDto.date.toLocalTime())) return "The parking lot it closed".left()
                if (parkingLot.value.checkIsFull()) return "The parking lot is full".left()
                return saveParkedCar(parkingLotId, parkedCarDto)
            }
        }
    }

    private fun saveParkedCar(parkingLotId: Long, parkedCarDto: ParkedCarDto): Either<String, ParkedCar> {
        val parkedCar = parkingLotRepository.saveParkedCar(parkedCarDto, parkingLotId)
        return parkedCar?.right() ?: "Was not possible to save the parked car".left()
    }
}
