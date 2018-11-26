package mjs.config

import mjs.shared.Checksum
import mjs.shared.DammChecksum
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChecksumConfiguration {
    @Bean
    fun checksum(): Checksum {
        return DammChecksum()
    }
}