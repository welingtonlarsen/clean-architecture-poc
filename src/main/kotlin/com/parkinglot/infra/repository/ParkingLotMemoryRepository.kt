package com.parkinglot.infra.repository

import com.parkinglot.adapter.ParkedCarAdapter
import com.parkinglot.adapter.ParkingLotAdapter
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import com.parkinglot.infra.database.memory.model.ParkedCarModel
import com.parkinglot.infra.database.memory.model.ParkingLotModel
import jakarta.inject.Singleton

@Singleton
class ParkingLotMemoryRepository : ParkingLotRepository {
    private val parkingLotMemory: MutableList<ParkingLotModel> = mutableListOf()
    private val parkedCarsMemory: MutableList<ParkedCarModel> = mutableListOf()

    override fun createParkingLot(parkingLotDto: ParkingLotDto): ParkingLot? {
        val nextId = if (parkingLotMemory.isNotEmpty()) parkingLotMemory.last().id.plus(1L) else 1L
        val model = ParkingLotModel(
            nextId, parkingLotDto.capacity, 0, parkingLotDto.openHour, parkingLotDto.closeHour
        )
        parkingLotMemory.add(model)
        return ParkingLotAdapter.create(
            nextId, parkingLotDto.capacity, 0, parkingLotDto.openHour, parkingLotDto.closeHour
        )
    }

    override fun getParkingLotById(id: Long): ParkingLot {
        val parkingLotModel = parkingLotMemory.first { it.id == id }
        return ParkingLotAdapter.create(
            parkingLotModel.id,
            parkingLotModel.capacity,
            parkingLotMemory.size,
            parkingLotModel.openHour,
            parkingLotModel.closeHour
        )
    }

    override fun saveParkedCar(parkedCarDto: ParkedCarDto): ParkedCar {
        val nextId = parkedCarsMemory.last().id.plus(1L)
        val parkedCarModel = ParkedCarModel(nextId, parkedCarDto.plate, parkedCarDto.date)
        parkedCarsMemory.add(parkedCarModel)
        return ParkedCarAdapter.create(parkedCarModel.id, parkedCarModel.plate, parkedCarModel.date)
    }

    override fun increaseParkingLotOccupiedSpace(parkingLotId: Long) {
        val parkingLotModelData = parkingLotMemory.first { it.id == parkingLotId }
        val parkingLotModelDataUpdated = ParkingLotModel(
            parkingLotModelData.id,
            parkingLotModelData.capacity,
            parkingLotModelData.occupiedSpaces + 1,
            parkingLotModelData.openHour,
            parkingLotModelData.closeHour
        )
        parkingLotMemory.removeIf { it.id == parkingLotId }
        parkingLotMemory.add(parkingLotModelDataUpdated)
    }
}
