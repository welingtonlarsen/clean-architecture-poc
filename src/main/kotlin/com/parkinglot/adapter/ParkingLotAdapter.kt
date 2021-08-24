package com.parkinglot.adapter

import com.parkinglot.core.entity.ParkingLot
import java.time.LocalTime

class ParkingLotAdapter {
    companion object {
        fun create(
            parkingLotId: Long,
            capacity: Int,
            occupiedSpaces: Int,
            openHour: LocalTime,
            closeHour: LocalTime
        ): ParkingLot =
            ParkingLot(parkingLotId, capacity, occupiedSpaces, openHour, closeHour)
    }
}
