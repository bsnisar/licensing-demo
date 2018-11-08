package mjs.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import mjs.projection.ApplicationView
import mjs.projection.IApplicantRepository
import mjs.projection.IApplicationRepository
import mjs.projection.IDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class QueryResolver(@Autowired val applicationRepository: IApplicationRepository,
                    @Autowired val documentRepository: IDocumentRepository,
                    @Autowired val applicantRepository: IApplicantRepository) : GraphQLQueryResolver {

    fun getApplicationById(id: String): ApplicationView? {
        val applicationId = UUID.fromString(id)
        val application = nullOpt(applicationRepository.findById(applicationId)) ?: return null
        return addApplicant(addDocuments(application))
    }

    fun applications(): List<ApplicationView> {
        return applicationRepository.findAll()
            .map { addDocuments(it) }
            .map { addApplicant(it) }
    }

    fun addDocuments(application: ApplicationView): ApplicationView {
        application.documents = documentRepository.findByApplicationId(application.id)
        return application
    }

    fun addApplicant(application: ApplicationView): ApplicationView {
        application.applicant = applicantRepository.findByApplicationId(application.id)
        return application
    }

    private fun <T> nullOpt(opt: Optional<T>): T? = if (opt.isPresent) opt.get() else null
}
