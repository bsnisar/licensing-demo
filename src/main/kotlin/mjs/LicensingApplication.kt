package mjs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LicensingApplication

fun main(args: Array<String>) {
    runApplication<LicensingApplication>(*args)
}
