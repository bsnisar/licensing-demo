package mjs.events

import java.time.Instant
import java.util.UUID

data class ApplicationCreatedEvent(
    val id: UUID,
    val number: Int,
    val type: String,
    val createTimestamp: Instant
)
