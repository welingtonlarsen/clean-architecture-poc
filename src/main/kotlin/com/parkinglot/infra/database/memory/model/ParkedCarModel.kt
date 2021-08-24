package com.parkinglot.infra.database.memory.model

import java.time.LocalDateTime

class ParkedCarModel(val id: Long, val plate: String, val date: LocalDateTime)
