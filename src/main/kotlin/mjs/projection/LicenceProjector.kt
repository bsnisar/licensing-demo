package mjs.projection

import mjs.events.LicenceCreatedEvent
import mjs.shared.Logging
import org.axonframework.eventhandling.EventHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LicenceProjector(@Autowired val repository: ILicenceRepository) {
    companion object {
        val log = Logging.loggerFor<LicenceProjector>()
    }

    @EventHandler
    fun on(event: LicenceCreatedEvent) {
        log.info("Handling LicenceCreatedEvent: {}", event)
        repository.save(LicenceView(
            event.id,
            event.type,
            event.createTimestamp
        ))
    }
}
