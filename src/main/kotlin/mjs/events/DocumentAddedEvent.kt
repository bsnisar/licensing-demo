package mjs.events

import java.util.UUID

data class DocumentAddedEvent(
    val licenceId: UUID,
    val id: UUID,
    val type: String,
    val contents: String
)
