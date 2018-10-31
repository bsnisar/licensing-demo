package mjs.model

import mjs.commands.CreateLicenceCommand
import mjs.events.LicenceCreatedEvent
import mjs.shared.Logging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.commandhandling.model.AggregateLifecycle
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import java.io.Serializable
import java.time.Instant
import java.util.UUID

@Aggregate
class Licence() : Serializable {

    companion object {
        val log = Logging.loggerFor<Licence>()
    }

    @AggregateIdentifier
    lateinit var id: UUID
    lateinit var type: String
    lateinit var createTimestamp: Instant

    @CommandHandler
    constructor(command: CreateLicenceCommand) : this() {
        log.info("Handling CreateLicenceCommand {}", command)
        AggregateLifecycle.apply(LicenceCreatedEvent(command.id, command.type, command.createTimestamp))
    }

    @EventSourcingHandler
    fun on(event: LicenceCreatedEvent) {
        log.info("Handling LicenceCreatedEvent {}", event)
        id = event.id
        type = event.type
        createTimestamp = event.createTimestamp
    }
}