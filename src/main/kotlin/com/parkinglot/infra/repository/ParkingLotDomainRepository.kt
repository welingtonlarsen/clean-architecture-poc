package com.parkinglot.infra.repository

import com.parkinglot.adapter.ParkedCarAdapter
import com.parkinglot.adapter.ParkingLotAdapter
import com.parkinglot.adapter.ParkingLotModelAdapter
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import com.parkinglot.infra.database.postgresql.ParkedCarJpaRepository
import com.parkinglot.infra.database.postgresql.ParkingLotJpaRepository
import com.parkinglot.infra.database.postgresql.model.ParkedCarModel
import jakarta.inject.Singleton
import javax.transaction.Transactional

@Singleton
open class ParkingLotDomainRepository(
    private val parkingLotJpaRepository: ParkingLotJpaRepository,
    private val parkedCarJpaRepository: ParkedCarJpaRepository
) :
    ParkingLotRepository {

    // TODO: Implement undo persistence actions when have exception
    override fun createParkingLot(parkingLotDto: ParkingLotDto): ParkingLot? {
        return try {
            val model = parkingLotJpaRepository.save(
                ParkingLotModelAdapter.createNew(
                    parkingLotDto.capacity,
                    parkingLotDto.openHour,
                    parkingLotDto.closeHour
                )
            )

            if (model.id == null || model.capacity == null || model.openHour == null || model.closeHour == null) {
                throw Exception("It was not possible to persist the parking lot")
            }

            ParkingLotAdapter.createFromAllParameters(
                model.id, model.capacity, model.openHour, model.closeHour, emptyList()
            )
        } catch (e: Exception) {
            null
        }
    }

    // TODO: Implement cache
    @Transactional
    override fun getParkingLotById(id: Long): ParkingLot? {
        val parkingLotModel = parkingLotJpaRepository.findById(id).get()

        return if (parkingLotModel.id != null) {
            val parkedCars =
                parkingLotModel.parkedCars.map {
                    ParkedCarAdapter.createFromAllParameters(
                        it.id!!,
                        it.plate!!,
                        it.date!!,
                        it.parkingLotModel!!.id!!
                    )
                }

            ParkingLotAdapter.createFromAllParameters(
                parkingLotModel.id,
                parkingLotModel.capacity!!,
                parkingLotModel.openHour!!,
                parkingLotModel.closeHour!!,
                parkedCars
            )
        } else {
            null
        }
    }

    override fun saveParkedCar(parkedCarDto: ParkedCarDto, parkingLotId: Long): ParkedCar? {
        val parkingLotModel = parkingLotJpaRepository.findById(parkingLotId).get()
        val parkedCarModel =
            parkedCarJpaRepository.save(ParkedCarModel(null, parkedCarDto.plate, parkedCarDto.date, parkingLotModel))
        return ParkedCarAdapter.createFromAllParameters(
            parkedCarModel.id!!,
            parkedCarModel.plate!!,
            parkedCarModel.date!!,
            parkedCarModel.parkingLotModel!!.id!!
        )
    }
}
