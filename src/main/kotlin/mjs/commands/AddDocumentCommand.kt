package mjs.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

data class AddDocumentCommand(
    @TargetAggregateIdentifier val applicationId: UUID,
    val id: UUID,
    val type: String,
    val contents: String
)
