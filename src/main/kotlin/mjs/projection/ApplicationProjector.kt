package mjs.projection

import mjs.events.ApplicationCreatedEvent
import mjs.shared.Logging
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ApplicationProjector(@Autowired val repository: IApplicationRepository) {
    companion object {
        val log = Logging.loggerFor<ApplicationProjector>()
    }

    @EventHandler
    fun on(event: ApplicationCreatedEvent) {
        log.info("Handling ApplicationCreatedEvent {}", event)
        repository.save(ApplicationView(
            event.id,
            event.type,
            event.createTimestamp
        ))
    }
}
