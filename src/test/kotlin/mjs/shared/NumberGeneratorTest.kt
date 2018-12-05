package mjs.shared

import assertk.assert
import assertk.assertions.isBetween
import assertk.assertions.isTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.RepeatedTest

internal class NumberGeneratorTest {

    private val checksum = DammChecksum()
    private val generator = NumberGenerator(checksum)

    @Nested
    inner class NextNumber {

        @RepeatedTest(100)
        fun `should return 9-digit numbers starting with 3`() {
            val number = generator.nextNumber()
            assert(number).isBetween(300_000_000, 399_999_999)
        }

        @RepeatedTest(100)
        fun `should return number with valid check digit`() {
            val number = generator.nextNumber()
            assert(checksum.isValid(number)).isTrue()
        }
    }
}