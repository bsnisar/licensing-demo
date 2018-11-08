package mjs.events

import java.util.UUID

data class ApplicantSetEvent(
    val applicationId: UUID,
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)
