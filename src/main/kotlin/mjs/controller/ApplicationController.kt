package mjs.controller

import mjs.commands.AddDocumentCommand
import mjs.commands.CreateApplicationCommand
import mjs.projection.AllApplicationsQuery
import mjs.projection.AllApplicationsResponse
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
@RequestMapping("/api/applications")
class ApplicationController(
    @Autowired val commandGateway: CommandGateway,
    @Autowired val queryGateway: QueryGateway,
    @Autowired val modelMapper: ModelMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createApplication(@Valid @RequestBody licenceRequest: ApplicationRequest): Future<Void> {
        return commandGateway.send(
            CreateApplicationCommand(
                licenceRequest.id,
                licenceRequest.type,
                licenceRequest.createTimestamp
            )
        )
    }

    @PostMapping("{applicationId}/documents")
    @ResponseStatus(HttpStatus.CREATED)
    fun addDocument(@PathVariable applicationId: UUID, @Valid @RequestBody documentRequest: DocumentRequest): Future<Void> {
        return commandGateway.send(
            AddDocumentCommand(
                applicationId,
                documentRequest.id,
                documentRequest.type,
                documentRequest.contents
            )
        )
    }

    @GetMapping
    fun getApplications(): CompletableFuture<List<ApplicationResponse>> {
        return queryGateway
            .query(AllApplicationsQuery(UUID.randomUUID()), AllApplicationsResponse::class.java)
            .thenApply { it.applications.map { licence -> modelMapper.map(licence, ApplicationResponse::class.java) } }
    }

    @GetMapping("{applicationId}/documents")
    fun getDocumentsForApplication(@PathVariable applicationId: UUID ): CompletableFuture<List<ApplicationResponse>> {
        return queryGateway
            .query(AllApplicationsQuery(UUID.randomUUID()), AllApplicationsResponse::class.java)
            .thenApply { it.applications.map { licence -> modelMapper.map(licence, ApplicationResponse::class.java) } }
    }
}

data class ApplicationRequest(
    val id: UUID,
    val type: String,
    val createTimestamp: Instant
)

class ApplicationResponse() {
    lateinit var id: UUID
    lateinit var type: String
    lateinit var createTimestamp: Instant
}

data class DocumentRequest(
    val id: UUID,
    val type: String,
    val contents: String
)
