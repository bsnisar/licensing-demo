package mjs.controller

import mjs.commands.CreateLicenceCommand
import mjs.projection.AllLicencesQuery
import mjs.projection.AllLicencesResponse
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.UUID
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import javax.validation.Valid

@RestController
@RequestMapping("/api/licences")
class LicenceController(
    @Autowired val commandGateway: CommandGateway,
    @Autowired val queryGateway: QueryGateway,
    @Autowired val modelMapper: ModelMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createLicence(@Valid @RequestBody licenceRequest: LicenceRequest): Future<Void> {
        return commandGateway.send(
            CreateLicenceCommand(
                licenceRequest.id,
                licenceRequest.type,
                licenceRequest.createTimestamp
            )
        )
    }

    @GetMapping
    fun getLicences(): CompletableFuture<List<LicenceResponse>> {
        return queryGateway
            .query(AllLicencesQuery(UUID.randomUUID()), AllLicencesResponse::class.java)
            .thenApply { it.licences.map { licence -> modelMapper.map(licence, LicenceResponse::class.java) } }
    }
}

data class LicenceRequest(
    val id: UUID,
    val type: String,
    val createTimestamp: Instant
)

class LicenceResponse() {
    lateinit var id: UUID
    lateinit var type: String
    lateinit var createTimestamp: Instant
}
