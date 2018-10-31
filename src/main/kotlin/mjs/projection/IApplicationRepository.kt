package mjs.projection

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface IApplicationRepository : JpaRepository<ApplicationView, UUID>
