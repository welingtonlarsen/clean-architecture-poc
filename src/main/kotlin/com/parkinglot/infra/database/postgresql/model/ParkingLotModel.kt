package com.parkinglot.infra.database.postgresql.model

import java.time.LocalTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class ParkingLotModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val capacity: Int?,
    val occupiedSpaces: Int?,
    val openHour: LocalTime?,
    val closeHour: LocalTime?,
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parkingLotModel")
    val parkedCars: MutableList<ParkedCarModel> = mutableListOf()
) {
    constructor() : this(null, null, null, null, null, mutableListOf())
}
