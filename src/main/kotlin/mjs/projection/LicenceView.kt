package mjs.projection

import java.time.Instant
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "licences")
class LicenceView(
    @Id
    val id: UUID,
    val type: String,
    val createDate: Instant
)
