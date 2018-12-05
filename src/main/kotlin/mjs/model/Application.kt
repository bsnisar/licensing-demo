package mjs.model

import mjs.commands.AddDocumentCommand
import mjs.commands.CreateApplicationCommand
import mjs.commands.SetApplicantCommand
import mjs.events.ApplicantSetEvent
import mjs.events.ApplicationCreatedEvent
import mjs.events.DocumentAddedEvent
import mjs.shared.Logging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
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
    var number: Int = 0
    lateinit var type: String
    lateinit var createTimestamp: Instant

    data class Applicant(val id: UUID, val firstName: String, val lastName: String, val email: String)

    lateinit var applicant: Applicant

    data class Document(val id: UUID, val type: String, val contents: String)

    val documents = ArrayList<Document>()

    @CommandHandler
    constructor(command: CreateApplicationCommand) : this() {
        log.info("Handling {}", command)
        AggregateLifecycle.apply(ApplicationCreatedEvent(command.id, command.number, command.type, command.createTimestamp))
    }

    @CommandHandler
    fun addDocument(command: AddDocumentCommand) {
        log.info("Handling {}", command)
        AggregateLifecycle.apply(DocumentAddedEvent(command.applicationId, command.id, command.type, command.contents))
    }

    @CommandHandler
    fun setApplicant(command: SetApplicantCommand) {
        log.info("Handling {}", command)
        AggregateLifecycle.apply(ApplicantSetEvent(command.applicationId, command.id, command.firstName, command.lastName, command.email))
    }

    @EventSourcingHandler
    fun on(event: ApplicationCreatedEvent) {
        log.info("Handling {}", event)
        id = event.id
        number = event.number
        type = event.type
        createTimestamp = event.createTimestamp
    }

    @EventSourcingHandler
    fun on(event: DocumentAddedEvent) {
        log.info("Handling {}", event)
        this.documents.add(Document(
            event.id,
            event.type,
            event.contents
        ))
    }

    @EventSourcingHandler
    fun on(event: ApplicantSetEvent) {
        log.info("Handling {}", event)
        this.applicant = Applicant(event.id, event.firstName, event.lastName, event.email)
    }
}