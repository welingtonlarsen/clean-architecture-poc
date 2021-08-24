package com.parkinglot.core.usecase

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
                every { parkingLotRepository.getParkingLotById(any()) } returns ParkingLot(
                    ID,
                    CAPACITY,
                    OCCUPIED_SPACES,
                    LocalTime.of(OPEN_HOUR, OPEN_CLOSE_MINUTE),
                    LocalTime.of(CLOSE_HOUR, OPEN_CLOSE_MINUTE)
                )
                val parkingLot = getParkingLot.execute(parkingLotId)

                Then("the parking lot should have been returned") {
                    parkingLot.id shouldBe ID
                    parkingLot.occupiedSpaces shouldBe OCCUPIED_SPACES
                    parkingLot.capacity shouldBe CAPACITY
                    parkingLot.openHour shouldBe LocalTime.of(OPEN_HOUR, OPEN_CLOSE_MINUTE)
                    parkingLot.closeHour shouldBe LocalTime.of(CLOSE_HOUR, OPEN_CLOSE_MINUTE)
                }
            }
        }
    }

    companion object {
        private const val ID = 1L
        private const val CAPACITY = 10
        private const val OCCUPIED_SPACES = 5
        private const val OPEN_HOUR = 10
        private const val CLOSE_HOUR = 18
        private const val OPEN_CLOSE_MINUTE = 0
    }
}
