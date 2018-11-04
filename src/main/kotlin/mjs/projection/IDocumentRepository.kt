package mjs.projection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDocumentRepository : JpaRepository<DocumentView, UUID> {
    fun findByApplicationId(applicationId: UUID): List<DocumentView>
}
