package mjs.events

import java.util.UUID

data class DocumentAddedEvent(
    val applicationId: UUID,
    val id: UUID,
    val type: String,
    val contents: String
)
