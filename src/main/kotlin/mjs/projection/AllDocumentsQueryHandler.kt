package mjs.projection

import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AllDocumentsQueryHandler(@Autowired val repository: IDocumentRepository) {
    @QueryHandler
    fun handle(query: AllDocumentsRequest) = AllDocumentsResponse(
        repository.findByApplicationId(query.applicationId)
    )
}

data class AllDocumentsRequest(val applicationId: UUID)
data class AllDocumentsResponse(val documents: List<DocumentView>)