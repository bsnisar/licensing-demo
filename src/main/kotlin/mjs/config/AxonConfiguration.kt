package mjs.config

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

    private fun jacksonSerializer(objectMapper: ObjectMapper): Serializer {
        objectMapper.registerKotlinModule()
        return JacksonSerializer(objectMapper)
    }

    @Primary
    @Bean
    fun serializer(objectMapper: ObjectMapper) = jacksonSerializer(objectMapper)

    @Qualifier("eventSerializer")
    @Bean
    fun eventSerializer(objectMapper: ObjectMapper) = jacksonSerializer(objectMapper)

    @Qualifier("messageSerializer")
    @Bean
    fun messageSerializer(objectMapper: ObjectMapper) = jacksonSerializer(objectMapper)
}
