package mjs.projection

import org.axonframework.queryhandling.QueryHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AllLicencesQueryHandler(@Autowired val repository: LicenceRepository) {
    @QueryHandler
    fun handle(query: AllLicencesQuery) = AllLicencesResponse(repository.findAll())
}

data class AllLicencesQuery(val id: UUID)
data class AllLicencesResponse(val licences: List<LicenceView>)