package com.parkinglot.core.usecase

import arrow.core.Either
import arrow.core.left
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.repository.ParkedCarRepository
import com.parkinglot.core.repository.ParkingLotRepository
import jakarta.inject.Singleton

@Singleton
class EnterCarInParkingLot(
    private val parkingLotRepository: ParkingLotRepository,
    private val parkedCarRepository: ParkedCarRepository
) {
    fun execute(parkingLotId: Long, parkedCarDto: ParkedCarDto): Either<String, ParkedCar> {
        return when (val parkingLot = parkingLotRepository.getParkingLotById(parkingLotId)) {
            is Either.Left -> Either.Left(parkingLot.value)
            is Either.Right -> {
                if (!parkingLot.value.isOpen(parkedCarDto.date.toLocalTime())) return "The parking lot it closed".left()
                if (parkingLot.value.checkIsFull()) return "The parking lot is full".left()
                return parkedCarRepository.saveParkedCar(parkedCarDto, parkingLotId)
            }
        }
    }
}
