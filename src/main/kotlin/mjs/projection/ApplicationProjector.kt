package mjs.projection

import mjs.events.ApplicantSetEvent
import mjs.events.ApplicationCreatedEvent
import mjs.events.DocumentAddedEvent
import mjs.shared.Logging
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApplicationProjector(
    @Autowired val applicationRepository: IApplicationRepository,
    @Autowired val documentRepository: IDocumentRepository,
    @Autowired val applicantRepository: IApplicantRepository
) {
    companion object {
        val log = Logging.loggerFor<ApplicationProjector>()
    }

    @EventHandler
    fun on(event: ApplicationCreatedEvent) {
        log.info("Projecting {}", event)
        applicationRepository.save(ApplicationView(
            event.id,
            event.type,
            event.createTimestamp
        ))
    }

    @EventHandler
    fun on(event: DocumentAddedEvent) {
        log.info("Projecting {}", event)
        documentRepository.save(DocumentView(
            event.id,
            event.applicationId,
            event.type,
            event.contents
        ))
    }

    @EventHandler
    fun on(event: ApplicantSetEvent) {
        log.info("Projecting {}", event)
        applicantRepository.save(ApplicantView(
            event.id,
            event.applicationId,
            event.firstName,
            event.lastName,
            event.email
        ))
    }
}
