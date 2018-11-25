package mjs.shared

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DammChecksumTest {

    @Nested
    inner class CalculateChecksumDigit {
        private val refNumbers = arrayOf(
            262632, 147425, 232403, 171024, 17220, 240474, 231782, 68566, 165346, 99618,
            214404, 115331, 111724, 117948, 242332, 202533, 96982, 240023, 133342, 250706)
        private val refChecks = arrayOf(
            Pair(6600, 9), Pair(5784, 1), Pair(22201, 5), Pair(14925, 5), Pair(1058, 8),
            Pair(14132, 2), Pair(24996, 0), Pair(29748, 4), Pair(22520, 3), Pair(7441, 7),
            Pair(15177, 7), Pair(5842, 2), Pair(17888, 6), Pair(12217, 7), Pair(15017, 5),
            Pair(15460, 0), Pair(14978, 3), Pair(16817, 3), Pair(4096, 0), Pair(19212, 3))

        @Test
        fun `should validate reference numbers`() {
            refNumbers.forEach { assert(DammChecksum.isValid(it)).isTrue() }
        }

        @Test
        fun `should calculate check digits for reference numbers`() {
            refChecks.forEach { assert(DammChecksum.checkDigit(it.first)).isEqualTo(it.second) }
            refChecks.forEach { assert(DammChecksum.checkSum(it.first)).isEqualTo(it.first * 10 + it.second) }
        }

        @Test
        fun `should ignore leading zeros in string numbers`() {
            assert(DammChecksum.checkDigit("6600")).isEqualTo(9)
            assert(DammChecksum.checkDigit("06600")).isEqualTo(9)
            assert(DammChecksum.checkDigit("006600")).isEqualTo(9)
            assert(DammChecksum.isValid("66009")).isTrue()
            assert(DammChecksum.isValid("066009")).isTrue()
            assert(DammChecksum.isValid("0066009")).isTrue()
            assert(DammChecksum.checkSum("6600")).isEqualTo("66009")
            assert(DammChecksum.checkSum("06600")).isEqualTo("066009")
            assert(DammChecksum.checkSum("006600")).isEqualTo("0066009")
        }
    }
}