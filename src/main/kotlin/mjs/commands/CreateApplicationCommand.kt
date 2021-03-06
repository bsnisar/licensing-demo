package mjs.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.Instant
import java.util.UUID

data class CreateApplicationCommand(
    @TargetAggregateIdentifier val id: UUID,
    val number: Int,
    val type: String,
    val createTimestamp: Instant
)
