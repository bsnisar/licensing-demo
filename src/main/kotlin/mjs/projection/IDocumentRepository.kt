package mjs.projection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IDocumentRepository : JpaRepository<DocumentView, UUID> {
    fun findByApplicationId(licenceId: UUID): List<DocumentView>
}
