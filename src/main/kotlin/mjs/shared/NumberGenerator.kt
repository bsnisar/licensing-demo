package mjs.shared

import kotlin.random.Random

object NumberGenerator {

    fun nextNumber(): Int {
        val base = 30_000_000
        val number = base + nextOne()
        return DammChecksum.generateCheckSum(number)
    }

    fun nextOne(): Int = Random.nextInt(10_000_000)
}