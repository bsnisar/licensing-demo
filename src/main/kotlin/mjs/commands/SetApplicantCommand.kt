package mjs.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class SetApplicantCommand(
    @TargetAggregateIdentifier val applicationId: UUID,
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)
