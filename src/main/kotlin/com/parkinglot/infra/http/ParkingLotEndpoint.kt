package com.parkinglot.infra.http

import arrow.core.Either
import com.parkinglot.controller.ParkingLotController
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

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
        return when (val parkedCar = parkingLotController.enterCarInParkingLot(parkingLotId, dto)) {
            is Either.Left -> HttpResponse.serverError()
            is Either.Right -> HttpResponse.created(parkedCar.value)
        }
    }

    @Get("/{parkingLotId}")
    fun getParkingLotById(@PathVariable parkingLotId: Long): HttpResponse<ParkingLot> {
        return parkingLotController.getParkingLotById(parkingLotId).fold(
            { HttpResponse.serverError() }, { HttpResponse.ok(it) }
        )
    }
}
