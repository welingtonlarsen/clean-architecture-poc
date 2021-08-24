package com.parkinglot.adapter

import com.parkinglot.core.entity.ParkedCar
import java.time.LocalDateTime

class ParkedCarAdapter {
    companion object {
        fun create(id: Long, plate: String, date: LocalDateTime): ParkedCar = ParkedCar(id, plate, date)
    }
}
