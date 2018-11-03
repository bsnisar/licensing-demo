package mjs.graphql

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import mjs.projection.ApplicationView
import mjs.projection.IApplicationRepository
import mjs.projection.IDocumentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class QueryResolver(@Autowired val applicationRepository: IApplicationRepository,
                    @Autowired val documentRepository: IDocumentRepository) : GraphQLQueryResolver {

    fun getApplicationById(id: String): Optional<ApplicationView> {
        val applicationId = UUID.fromString(id)
        val application = applicationRepository.findById(applicationId)
        if (application.isPresent) {
            val documents = documentRepository.findByApplicationId(applicationId)
            application.get().documents = documents
            return application
        }
        return Optional.empty()
    }
}
