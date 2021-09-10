package com.parkinglot.infra.repository

import arrow.core.Either
import com.parkinglot.adapter.ParkedCarAdapter
import com.parkinglot.adapter.ParkingLotAdapter
import com.parkinglot.adapter.ParkingLotModelAdapter
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import com.parkinglot.infra.database.postgresql.jparepository.ParkingLotJpaRepository
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ParkingLotDomainPostgresRepository(
    private val parkingLotJpaRepository: ParkingLotJpaRepository,
) : ParkingLotRepository {

    // TODO: Implement undo persistence actions when have exception (rollback)
    override fun createParkingLot(parkingLotDto: ParkingLotDto): Either<String, ParkingLot> {
        return try {
            val model = parkingLotJpaRepository.save(
                ParkingLotModelAdapter.createNew(
                    parkingLotDto.capacity,
                    parkingLotDto.openHour,
                    parkingLotDto.closeHour
                )
            )

            if (model.id == null || model.capacity == null || model.openHour == null || model.closeHour == null) {
                return Either.Left("It was not possible to persist the parking lot")
            }

            Either.Right(
                ParkingLotAdapter.createFromAllParameters(
                    model.id, model.capacity, model.openHour, model.closeHour, emptyList()
                )
            )
        } catch (e: Exception) {
            Either.Left("It was not possible to persist the parking lot")
        }
    }

    // TODO: Implement cache
    @Transactional
    override fun getParkingLotById(id: Long): Either<String, ParkingLot> {
        val parkingLotModel = parkingLotJpaRepository.findById(id)

        return if (parkingLotModel.isPresent) {
            val parkingLotModelFinded = parkingLotModel.get()

            val parkedCars =
                parkingLotModelFinded.parkedCars.map {
                    ParkedCarAdapter.createFromAllParameters(
                        it.id!!,
                        it.plate!!,
                        it.date!!,
                        it.parkingLotModel!!.id!!
                    )
                }

            val parkingLot = ParkingLotAdapter.createFromAllParameters(
                parkingLotModelFinded.id!!,
                parkingLotModelFinded.capacity!!,
                parkingLotModelFinded.openHour!!,
                parkingLotModelFinded.closeHour!!,
                parkedCars
            )

            Either.Right(parkingLot)
        } else {
            Either.Left("Parking lot with id $id doesn't exist")
        }
    }
}
