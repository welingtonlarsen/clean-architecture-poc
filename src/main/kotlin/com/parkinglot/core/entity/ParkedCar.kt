package com.parkinglot.core.entity

import java.time.LocalDateTime

data class ParkedCar(val id: Long, val plate: String, val date: LocalDateTime)
