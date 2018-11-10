package mjs.controller

import mjs.commands.AddDocumentCommand
import mjs.commands.CreateApplicationCommand
import mjs.commands.SetApplicantCommand
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
    fun createApplication(@Valid @RequestBody createApplicationRequest: CreateApplicationRequest): Future<Void> {
        return commandGateway.send(
            CreateApplicationCommand(
                createApplicationRequest.id,
                createApplicationRequest.type,
                createApplicationRequest.createTimestamp
            )
        )
    }

    @PostMapping("{applicationId}/applicant")
    @ResponseStatus(HttpStatus.CREATED)
    fun setApplicant(@PathVariable applicationId: UUID, @Valid @RequestBody setApplicantRequest: SetApplicantRequest): Future<Void> {
        return commandGateway.send(
            SetApplicantCommand(
                applicationId,
                setApplicantRequest.id,
                setApplicantRequest.firstName,
                setApplicantRequest.lastName,
                setApplicantRequest.email
            )
        )
    }

    @PostMapping("{applicationId}/documents")
    @ResponseStatus(HttpStatus.CREATED)
    fun addDocument(@PathVariable applicationId: UUID, @Valid @RequestBody addDocumentRequest: AddDocumentRequest): Future<Void> {
        return commandGateway.send(
            AddDocumentCommand(
                applicationId,
                addDocumentRequest.id,
                addDocumentRequest.type,
                addDocumentRequest.contents
            )
        )
    }

    @GetMapping
    fun getApplications(): CompletableFuture<List<CreateApplicationResponse>> {
        return queryGateway
            .query(AllApplicationsQuery(UUID.randomUUID()), AllApplicationsResponse::class.java)
            .thenApply { it.applications.map { application -> modelMapper.map(application, CreateApplicationResponse::class.java) } }
    }

    @GetMapping("{applicationId}/documents")
    fun getDocumentsForApplication(@PathVariable applicationId: UUID): CompletableFuture<List<CreateApplicationResponse>> {
        return queryGateway
            .query(AllApplicationsQuery(UUID.randomUUID()), AllApplicationsResponse::class.java)
            .thenApply { it.applications.map { application -> modelMapper.map(application, CreateApplicationResponse::class.java) } }
    }
}

data class CreateApplicationRequest(
    val id: UUID,
    val type: String,
    val createTimestamp: Instant
)

class CreateApplicationResponse {
    lateinit var id: UUID
    lateinit var type: String
    lateinit var createTimestamp: Instant
}

data class SetApplicantRequest(
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val email: String
)

data class AddDocumentRequest(
    val id: UUID,
    val type: String,
    val contents: String
)
