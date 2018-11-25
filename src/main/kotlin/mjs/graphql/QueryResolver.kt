package mjs.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import mjs.projection.ApplicationView
import mjs.projection.IApplicantRepository
import mjs.projection.IApplicationRepository
import mjs.projection.IDocumentRepository
import mjs.shared.DammChecksum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class QueryResolver(
    @Autowired val applicationRepository: IApplicationRepository,
    @Autowired val documentRepository: IDocumentRepository,
    @Autowired val applicantRepository: IApplicantRepository
) : GraphQLQueryResolver {

    fun getApplicationById(applicationId: UUID): ApplicationView? {
        val application = nullOpt(applicationRepository.findById(applicationId)) ?: return null
        return loadApplicant(loadDocuments(application))
    }

    fun getApplicationByNumber(number: Int): ApplicationView? {
        if (!DammChecksum.isValid(number)) {
            throw IllegalArgumentException("Invalid application number $number")
        }
        val application = nullOpt(applicationRepository.findByNumber(number)) ?: return null
        return application
            .also { loadApplicant(it) }
            .also { loadDocuments(it) }
    }

    fun applications(): List<ApplicationView> {
        return applicationRepository.findAll()
            .map { loadApplicant(it) }
            .map { loadDocuments(it) }
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
