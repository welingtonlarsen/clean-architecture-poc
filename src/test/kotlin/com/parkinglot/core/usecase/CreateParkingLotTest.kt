package com.parkinglot.core.usecase

import arrow.core.Either
import com.parkinglot.core.dto.ParkingLotDto
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import java.time.LocalTime

class CreateParkingLotTest : BehaviorSpec() {
    @MockK
    private lateinit var parkingLotRepository: ParkingLotRepository

    @InjectMockKs
    private lateinit var createParkingLot: CreateParkingLot

    override fun beforeSpec(spec: Spec) {
        MockKAnnotations.init(this)
    }

    init {
        Given("a parking lot dto") {
            val parkingLotDto = ParkingLotDto(
                CAPACITY, LocalTime.of(OPEN_HOUR, OPEN_CLOSE_MINUTE),
                LocalTime.of(CLOSE_HOUR, OPEN_CLOSE_MINUTE)
            )

            When("creating a parking lot") {
                every { parkingLotRepository.createParkingLot(any()) } returns Either.Right(
                    ParkingLot(
                        ID,
                        parkingLotDto.capacity,
                        parkingLotDto.openHour,
                        parkingLotDto.closeHour,
                        listOf()
                    )
                )

                val parkingLot = createParkingLot.execute(parkingLotDto)

                Then("the parking lot should have been created") {
                    parkingLot shouldBe Either.Right(
                        ParkingLot(
                            ID,
                            parkingLotDto.capacity,
                            parkingLotDto.openHour,
                            parkingLotDto.closeHour,
                            listOf()
                        )
                    )
                    parkingLot shouldNotBe Either.Left
                }
            }
        }
    }

    companion object {
        private const val ID = 1L
        private const val CAPACITY = 10
        private const val OPEN_HOUR = 9
        private const val CLOSE_HOUR = 18
        private const val OPEN_CLOSE_MINUTE = 0
    }
}
