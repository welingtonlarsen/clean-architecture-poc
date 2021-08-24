package com.parkinglot.core.repository

import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot

interface ParkingLotRepository {
    fun createParkingLot(parkingLotDto: ParkingLotDto): ParkingLot?
    fun getParkingLotById(id: Long): ParkingLot?
    fun saveParkedCar(parkedCarDto: ParkedCarDto): ParkedCar?
    fun increaseParkingLotOccupiedSpace(parkingLotId: Long)
}
