package com.parkinglot.infra.repository

import arrow.core.Either
import com.parkinglot.adapter.ParkedCarAdapter
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.repository.ParkedCarRepository
import com.parkinglot.infra.database.postgresql.jparepository.ParkedCarJpaRepository
import com.parkinglot.infra.database.postgresql.jparepository.ParkingLotJpaRepository
import com.parkinglot.infra.database.postgresql.model.ParkedCarModel
import jakarta.inject.Singleton

@Singleton
class ParkedCarDomainPostgresRepository(
    private val parkingLotJpaRepository: ParkingLotJpaRepository,
    private val parkedCarJpaRepository: ParkedCarJpaRepository
) : ParkedCarRepository {
    override fun saveParkedCar(parkedCarDto: ParkedCarDto, parkingLotId: Long): Either<String, ParkedCar> {
        return try {
            val parkingLotModel = parkingLotJpaRepository.findById(parkingLotId).get()
            val parkedCarModel = parkedCarJpaRepository.save(
                ParkedCarModel(
                    null, parkedCarDto.plate,
                    parkedCarDto.date,
                    parkingLotModel
                )
            )
            Either.Right(
                ParkedCarAdapter.createFromAllParameters(
                    parkedCarModel.id!!,
                    parkedCarModel.plate!!,
                    parkedCarModel.date!!,
                    parkedCarModel.parkingLotModel!!.id!!
                )
            )
        } catch (e: Exception) {
            Either.Left("It was not possible to save in data base the parked car with plate ${parkedCarDto.plate}")
        }
    }
}
