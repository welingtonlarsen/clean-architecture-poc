package com.parkinglot.infra.database.postgresql

import com.parkinglot.infra.database.postgresql.model.ParkedCarModel
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface ParkedCarJpaRepository : JpaRepository<ParkedCarModel, Long>
