package com.parkinglot.adapter

import com.parkinglot.core.entity.ParkedCar
import java.time.LocalDateTime

class ParkedCarAdapter {
    companion object {
        fun createFromAllParameters(id: Long, plate: String, date: LocalDateTime, parkingLotId: Long) =
            ParkedCar(id, plate, date, parkingLotId)
    }
}
