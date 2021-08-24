package com.parkinglot.infra.http

import com.parkinglot.controller.ParkingLotController
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
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
}
