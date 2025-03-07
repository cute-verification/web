package io.github.gdrfgdrf.cuteverification.web.services.impl.query

import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionTargetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class QueryIdentificationService : IQueryService.IIdentificationService {
    @Autowired
    private lateinit var restrictionService: IRestrictionService
    @Autowired
    private lateinit var restrictionTargetService: IRestrictionTargetService

    @Transactional
    override fun restrictions(
        identificationId: String,
        link: Boolean
    ): List<Restriction> {
        val restrictionIds = restrictionTargetService.ktQuery()
            .select(RestrictionTarget::restrictionId, RestrictionTarget::identificationId)
            .eq(RestrictionTarget::identificationId, identificationId)
            .list()
            .map {
                it.restrictionId
            }
        if (restrictionIds.isEmpty()) {
            return arrayListOf()
        }

        val restrictions = restrictionService.ktQuery()
            .`in`(Restriction::id, restrictionIds)
            .eq(Restriction::deleted, false)
            .list()

        if (link) {
            restrictionService.autoMapper.mapperEntityList<Restriction>(restrictions)
        }
        return restrictions
    }
}