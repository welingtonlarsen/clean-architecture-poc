package com.parkinglot.infra.database.postgresql.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class ParkedCarModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val plate: String?,
    val date: LocalDateTime?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARKING_LOT_ID")
    val parkingLotModel: ParkingLotModel?
) {
    constructor() : this(null, null, null, null)
}
