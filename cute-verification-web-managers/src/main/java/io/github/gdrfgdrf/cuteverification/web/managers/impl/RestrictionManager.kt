package io.github.gdrfgdrf.cuteverification.web.managers.impl

import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestriction
import io.github.gdrfgdrf.cuteverification.web.managers.IRestrictionManager
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.services.restriction.IRestrictionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RestrictionManager : IRestrictionManager {
    @Autowired
    private lateinit var restrictionService: IRestrictionService

    override fun existsById(id: String): Boolean {
        val query = restrictionService.ktQuery()
            .eq(Restriction::id, id)
            .eq(Restriction::deleted, false)
        return restrictionService.exists(query)
    }

    override fun findById(id: String): IRestriction {
        return restrictionService.getById(id)
    }

    override fun create(restriction: IRestriction) {
        restrictionService.save(restriction as Restriction)
    }

    override fun deleteById(id: String) {
        restrictionService.removeById(id)
    }

    override fun list(): List<IRestriction> {
        return restrictionService.list()
    }
}