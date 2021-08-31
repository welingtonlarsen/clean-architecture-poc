package com.parkinglot.adapter

import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import java.time.LocalTime

class ParkingLotAdapter {
    companion object {
        fun createFromAllParameters(
            id: Long,
            capacity: Int,
            openHour: LocalTime,
            closeHour: LocalTime,
            parkedCars: List<ParkedCar>
        ) = ParkingLot(id, capacity, openHour, closeHour, parkedCars)
    }
}
