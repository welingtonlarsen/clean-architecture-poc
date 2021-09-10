package com.parkinglot.core.usecase

import arrow.core.Either
import com.parkinglot.core.entity.ParkingLot
import com.parkinglot.core.repository.ParkingLotRepository
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import java.time.LocalTime

class GetParkingLotTest : BehaviorSpec() {
    @MockK
    private lateinit var parkingLotRepository: ParkingLotRepository

    @InjectMockKs
    private lateinit var getParkingLot: GetParkingLot

    override fun beforeSpec(spec: Spec) {
        MockKAnnotations.init(this)
    }

    init {
        Given("a parking lot id") {
            val parkingLotId = ID

            When("requesting for a parking lot") {
                every { parkingLotRepository.getParkingLotById(any()) } returns Either.Right(
                    ParkingLot(
                        ID,
                        CAPACITY,
                        LocalTime.of(OPEN_HOUR, OPEN_CLOSE_MINUTE),
                        LocalTime.of(CLOSE_HOUR, OPEN_CLOSE_MINUTE),
                        listOf()
                    )
                )

                val parkingLot = getParkingLot.execute(parkingLotId)

                Then("the parking lot should have been returned") {
                    parkingLot shouldBe Either.Right(
                        ParkingLot(
                            ID,
                            CAPACITY,
                            LocalTime.of(OPEN_HOUR, OPEN_CLOSE_MINUTE),
                            LocalTime.of(CLOSE_HOUR, OPEN_CLOSE_MINUTE),
                            listOf()
                        )
                    )
                }
            }
        }
    }

    companion object {
        private const val ID = 1L
        private const val CAPACITY = 10
        private const val OPEN_HOUR = 10
        private const val CLOSE_HOUR = 18
        private const val OPEN_CLOSE_MINUTE = 0
    }
}
