package mjs.projection

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "documents")
class DocumentView(
    @Id
    val id: UUID,
    val applicationId: UUID,
    val type: String,
    val contents: String
)
