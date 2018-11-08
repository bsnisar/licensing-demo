package mjs.projection

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "applicants")
class ApplicantView(
    @Id
    val id: UUID,
    val applicationId: UUID,
    val firstName: String?,
    val lastName: String,
    val email: String
)
