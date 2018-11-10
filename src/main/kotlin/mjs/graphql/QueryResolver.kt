package mjs.graphql

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.coxautodev.graphql.tools.GraphQLQueryResolver
import mjs.projection.ApplicantView
import mjs.projection.ApplicationView
import mjs.projection.DocumentView
import mjs.projection.IApplicantRepository
import mjs.projection.IApplicationRepository
import mjs.projection.IDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Optional
import java.util.UUID

@Component
class QueryResolver(
    @Autowired val applicationRepository: IApplicationRepository,
    @Autowired val documentRepository: IDocumentRepository,
    @Autowired val applicantRepository: IApplicantRepository
) : GraphQLQueryResolver, GraphQLMutationResolver {

    fun getApplicationById(applicationId: UUID): ApplicationView? {
        val application = nullOpt(applicationRepository.findById(applicationId)) ?: return null
        return loadApplicant(loadDocuments(application))
    }

    fun applications(): List<ApplicationView> {
        return applicationRepository.findAll()
            .map { loadDocuments(it) }
            .map { loadApplicant(it) }
    }

    fun createApplication(applicationId: UUID, type: String, createTimestamp: String): ApplicationView {
        return applicationRepository.save(ApplicationView(applicationId, type, Instant.parse(createTimestamp)))
    }

    fun addDocument(applicationId: UUID, id: UUID, type: String, contents: String): ApplicationView? {
        val application = nullOpt(applicationRepository.findById(applicationId)) ?: return null
        application.documents = listOf(documentRepository.save(DocumentView(id, applicationId, type, contents)))
        return application
    }

    fun setApplicant(applicationId: UUID, id: UUID, firstName: String, lastName: String, email: String): ApplicationView? {
        val application = nullOpt(applicationRepository.findById(applicationId)) ?: return null
        application.applicant = applicantRepository.save(ApplicantView(id, applicationId, firstName, lastName, email))
        return application
    }

    fun loadDocuments(application: ApplicationView): ApplicationView {
        application.documents = documentRepository.findByApplicationId(application.id)
        return application
    }

    fun loadApplicant(application: ApplicationView): ApplicationView {
        application.applicant = applicantRepository.findByApplicationId(application.id)
        return application
    }

    private fun <T> nullOpt(opt: Optional<T>): T? = if (opt.isPresent) opt.get() else null
}
