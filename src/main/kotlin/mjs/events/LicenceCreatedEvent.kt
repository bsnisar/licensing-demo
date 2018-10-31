package mjs.events

import java.time.Instant
import java.util.UUID

data class LicenceCreatedEvent(
    val id: UUID,
    val type: String,
    val createTimestamp: Instant
)
