package mjs

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {

    @Bean
    fun modelMapper(): ModelMapper = ModelMapper()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
