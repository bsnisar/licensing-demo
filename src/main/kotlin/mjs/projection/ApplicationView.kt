package mjs.projection

import java.time.Instant
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "applications")
class ApplicationView(
    @Id
    val id: UUID,
    val type: String,
    val createTimestamp: Instant
)
