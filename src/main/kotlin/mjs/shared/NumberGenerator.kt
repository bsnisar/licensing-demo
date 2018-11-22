package mjs.shared

import kotlin.random.Random

object NumberGenerator {

    fun nextNumber(): Int {
        val base = 30_000_000
        val number = base + nextOne()
        return number * 10 + checkDigit(number)
    }

    fun nextOne(): Int = Random.nextInt(10_000_000)

    fun checkDigit(number: Int): Int = 7

}