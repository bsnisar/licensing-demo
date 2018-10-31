package mjs.projection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface LicenceRepository : JpaRepository<LicenceView, UUID>
