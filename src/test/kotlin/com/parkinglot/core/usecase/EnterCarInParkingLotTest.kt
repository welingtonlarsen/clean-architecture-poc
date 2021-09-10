package com.parkinglot.core.usecase

import arrow.core.Either
import com.parkinglot.core.dto.ParkedCarDto
import com.parkinglot.core.entity.ParkedCar
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkedCarRepository
import com.parkinglot.core.repository.ParkingLotRepository
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import java.time.LocalDateTime
import java.time.LocalTime

class EnterCarInParkingLotTest : BehaviorSpec() {
    @MockK
    private lateinit var parkingLotRepository: ParkingLotRepository

    @MockK
    private lateinit var parkedCarRepository: ParkedCarRepository

    @InjectMockKs
    private lateinit var enterCarInParkingLot: EnterCarInParkingLot

    override fun beforeSpec(spec: Spec) {
        MockKAnnotations.init(this)
    }

    init {
        Given("a parking lot id and a parked car dto") {
            val parkingLotId = PARKING_LOT_ID
            val parkedCarDto = ParkedCarDto(PLATE, LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE))

            When("parking the car") {
                mockToParkingTheCar()
                val parkedCar = enterCarInParkingLot.execute(parkingLotId, parkedCarDto)

                Then("the car should have been parked") {
                    parkedCar shouldBe Either.Right(
                        ParkedCar(
                            PARKED_CAR_ID,
                            PLATE,
                            LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE),
                            PARKING_LOT_ID
                        )
                    )
                    parkedCar shouldNotBe Either.Left
                }
            }
            When("parking the car in a closed parking lot") {
                every { parkingLotRepository.getParkingLotById(any()) } returns Either.Right(
                    ParkingLot(
                        PARKING_LOT_ID, CAPACITY, LocalTime.of(PARKED_HOUR, OPEN_MINUTE),
                        LocalTime.of(CLOSE_HOUR, CLOSE_MINUTE),
                        listOf(
                            ParkedCar(
                                PARKED_CAR_ID,
                                PLATE,
                                LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE),
                                PARKING_LOT_ID
                            )
                        )
                    )
                )

                val response = enterCarInParkingLot.execute(parkingLotId, parkedCarDto)

                Then("the car should not have been parked") {
                    response shouldBe Either.Left("The parking lot it closed")
                }
            }
            When("parking the car in a full parking lot") {
                every { parkingLotRepository.getParkingLotById(any()) } returns Either.Right(
                    ParkingLot(
                        PARKING_LOT_ID, ZERO_CAPACITY, LocalTime.of(OPEN_HOUR, OPEN_MINUTE),
                        LocalTime.of(CLOSE_HOUR, CLOSE_MINUTE),
                        listOf(
                            ParkedCar(
                                PARKED_CAR_ID,
                                PLATE,
                                LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE),
                                PARKING_LOT_ID
                            )
                        )
                    )
                )

                val response = enterCarInParkingLot.execute(parkingLotId, parkedCarDto)

                Then("the car should not have been parked") {
                    response shouldBe Either.Left("The parking lot is full")
                }
            }
        }
    }

    private fun mockToParkingTheCar() {
        every { parkingLotRepository.getParkingLotById(any()) } returns Either.Right(
            ParkingLot(
                PARKING_LOT_ID,
                CAPACITY,
                LocalTime.of(OPEN_HOUR, OPEN_MINUTE),
                LocalTime.of(CLOSE_HOUR, CLOSE_MINUTE),
                listOf(
                    ParkedCar(
                        PARKED_CAR_ID,
                        PLATE,
                        LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE),
                        PARKING_LOT_ID
                    )
                )
            )
        )
        every { parkedCarRepository.saveParkedCar(any(), any()) } returns Either.Right(
            ParkedCar(
                PARKED_CAR_ID,
                PLATE,
                LocalDateTime.of(YEAR, MONTH, DAY, PARKED_HOUR, PARKED_MINUTE),
                PARKING_LOT_ID
            )
        )
    }

    companion object {
        private const val PARKING_LOT_ID = 1L
        private const val PARKED_CAR_ID = 1L
        private const val PLATE = "MGH-4080"
        private const val YEAR = 2021
        private const val MONTH = 1
        private const val DAY = 1
        private const val PARKED_HOUR = 11
        private const val PARKED_MINUTE = 0
        private const val CAPACITY = 10
        private const val ZERO_CAPACITY = 0
        private const val OPEN_HOUR = 9
        private const val OPEN_MINUTE = 0
        private const val CLOSE_HOUR = 18
        private const val CLOSE_MINUTE = 0
    }
}
