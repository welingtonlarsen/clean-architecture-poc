package com.parkinglot.core.repository

import arrow.core.Either
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar

interface ParkedCarRepository {
    fun saveParkedCar(parkedCarDto: ParkedCarDto, parkingLotId: Long): Either<String, ParkedCar>
}
