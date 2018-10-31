package mjs.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.Instant
import java.util.UUID

data class CreateLicenceCommand(
    @TargetAggregateIdentifier val id: UUID,
    val type: String,
    val createDate: Instant
)
