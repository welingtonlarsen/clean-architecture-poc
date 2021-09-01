package com.parkinglot.infra.http

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.left
import arrow.core.right
import com.parkinglot.controller.ParkingLotController
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Get

@Controller("/parking-lot")
class ParkingLotEndpoint(private val parkingLotController: ParkingLotController) {
    @Post("/create")
    fun createParkingLot(@Body dto: ParkingLotDto): HttpResponse<ParkingLot> {
        val parkingLot = parkingLotController.createParkingLot(dto)
        parkingLot?.let {
            return HttpResponse.created(it)
        }
        return HttpResponse.serverError()
    }

    @Post("/enter-car/{parkingLotId}")
    fun enterCarInParkingLot(@PathVariable parkingLotId: Long, @Body dto: ParkedCarDto): HttpResponse<ParkedCar> {
        val parkedCar = parkingLotController.enterCarInParkingLot(parkingLotId, dto)
        parkedCar?.let { return HttpResponse.created(it) }
        return HttpResponse.serverError()
    }

    @Get("/{parkingLotId}")
    fun getParkingLotById(@PathVariable parkingLotId: Long): HttpResponse<ParkingLot> {
        return when (val parkingLot = parkingLotController.getParkingLotById(parkingLotId)) {
            is Either.Left -> {
                HttpResponse.serverError()
            }
            is Either.Right -> {
                HttpResponse.ok(parkingLot.value)
            }
        }
    }
}
