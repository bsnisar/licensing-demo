package mjs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.axonframework.serialization.Serializer
import org.axonframework.serialization.json.JacksonSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AxonConfiguration {
    @Primary
    @Bean
    fun serializer(objectMapper: ObjectMapper): Serializer {
        objectMapper.registerKotlinModule()
        return JacksonSerializer(objectMapper)
    }

    @Qualifier("eventSerializer")
    @Bean
    fun eventSerializer(objectMapper: ObjectMapper): Serializer {
        objectMapper.registerKotlinModule()
        return JacksonSerializer(objectMapper)
    }

    @Qualifier("messageSerializer")
    @Bean
    fun messageSerializer(objectMapper: ObjectMapper): Serializer {
        objectMapper.registerKotlinModule()
        return JacksonSerializer(objectMapper)
    }
}
