package mjs.shared

import assertk.assert
import assertk.assertions.isBetween
import assertk.assertions.isTrue
import mjs.shared.NumberGenerator.nextNumber
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.RepeatedTest

internal class NumberGeneratorTest {

    @Nested
    inner class NextNumber {

        @RepeatedTest(100)
        fun `should return 9-digit numbers starting with 3`() {
            val number = nextNumber()
            assert(number).isBetween(300_000_000, 399_999_999)
        }

        @RepeatedTest(100)
        fun `should return number with valid check digit`() {
            val number = nextNumber()
            assert(DammChecksum.validate(number)).isTrue()
        }
    }

}