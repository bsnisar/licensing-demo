package mjs.projection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface IApplicationRepository : JpaRepository<ApplicationView, UUID> {
    fun findByNumber(number: Int): Optional<ApplicationView>
}

interface IDocumentRepository : JpaRepository<DocumentView, UUID> {
    fun findByApplicationId(applicationId: UUID): List<DocumentView>
}

interface IApplicantRepository : JpaRepository<ApplicantView, UUID> {
    fun findByApplicationId(applicationId: UUID): ApplicantView?
}
