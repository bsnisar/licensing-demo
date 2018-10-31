package mjs.shared

import org.slf4j.LoggerFactory

class Logging {
    companion object {
        inline fun <reified T : Any> loggerFor() = LoggerFactory.getLogger(T::class.java)
    }
}