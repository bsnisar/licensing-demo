package mjs

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class LicensingApplication {

    @Bean
    fun modelMapper(): ModelMapper = ModelMapper()
}

fun main(args: Array<String>) {
    runApplication<LicensingApplication>(*args)
}
