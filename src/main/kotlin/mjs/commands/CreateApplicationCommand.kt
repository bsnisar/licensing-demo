package mjs.commands

import org.axonframework.commandhandling.TargetAggregateIdentifier
import java.time.Instant
import java.util.UUID

data class CreateApplicationCommand(
    @TargetAggregateIdentifier val id: UUID,
    val type: String,
    val createTimestamp: Instant
)
