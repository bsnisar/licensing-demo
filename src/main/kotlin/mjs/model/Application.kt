package mjs.model

import mjs.commands.CreateApplicationCommand
import mjs.events.ApplicationCreatedEvent
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
class Application() : Serializable {

    companion object {
        val log = Logging.loggerFor<Application>()
    }

    @AggregateIdentifier
    lateinit var id: UUID
    lateinit var type: String
    lateinit var createTimestamp: Instant

    @CommandHandler
    constructor(command: CreateApplicationCommand) : this() {
        log.info("Handling CreateApplicationCommand {}", command)
        AggregateLifecycle.apply(ApplicationCreatedEvent(command.id, command.type, command.createTimestamp))
    }

    @EventSourcingHandler
    fun on(event: ApplicationCreatedEvent) {
        log.info("Handling ApplicationCreatedEvent {}", event)
        id = event.id
        type = event.type
        createTimestamp = event.createTimestamp
    }
}