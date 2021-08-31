package com.parkinglot.adapter

import com.parkinglot.infra.database.postgresql.model.ParkingLotModel
import java.time.LocalTime

class ParkingLotModelAdapter {
    companion object {
        fun createNew(capacity: Int, openHour: LocalTime, closeHour: LocalTime) = ParkingLotModel(
            NO_PERSISTED_ID,
            capacity,
            INITIAL_OCCUPIED_SPACES,
            openHour,
            closeHour,
            mutableListOf()
        )

        private val NO_PERSISTED_ID = null
        private const val INITIAL_OCCUPIED_SPACES = 0
    }
}
