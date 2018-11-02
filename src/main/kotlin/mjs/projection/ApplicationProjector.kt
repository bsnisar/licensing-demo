package mjs.projection

import mjs.events.ApplicationCreatedEvent
import mjs.events.DocumentAddedEvent
import mjs.shared.Logging
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApplicationProjector(@Autowired val applicationRepository: IApplicationRepository,
                           @Autowired val documentRepository: IDocumentRepository) {
    companion object {
        val log = Logging.loggerFor<ApplicationProjector>()
    }

    @EventHandler
    fun on(event: ApplicationCreatedEvent) {
        log.info("Handling ApplicationCreatedEvent {}", event)
        applicationRepository.save(ApplicationView(
            event.id,
            event.type,
            event.createTimestamp
        ))
    }

    @EventHandler
    fun on(event: DocumentAddedEvent) {
        log.info("Handling DocumentAddedEvent {}", event)
        documentRepository.save(DocumentView(
            event.id,
            event.licenceId,
            event.type,
            event.contents
        ))
    }
}
