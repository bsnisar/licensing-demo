package mjs.projection

import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AllApplicationsQueryHandler(@Autowired val repository: IApplicationRepository) {
    @QueryHandler
    fun handle(query: AllApplicationsQuery) = AllApplicationsResponse(repository.findAll())
}

data class AllApplicationsQuery(val id: UUID)
data class AllApplicationsResponse(val applications: List<ApplicationView>)