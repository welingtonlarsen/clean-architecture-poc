package io.kotest.provided

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class Teste123 : BehaviorSpec() {
    init {
        Given("oie") {
            Then("haha") {
                true shouldBe true
            }
        }
    }
}
