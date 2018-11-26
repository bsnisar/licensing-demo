package mjs.shared

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class NumberGenerator(@Autowired val checksum: Checksum) {

    fun nextNumber(): Int {
        val base = 30_000_000
        val number = base + nextOne()
        return checksum.checkSum(number)
    }

    fun nextOne(): Int = Random.nextInt(10_000_000)
}