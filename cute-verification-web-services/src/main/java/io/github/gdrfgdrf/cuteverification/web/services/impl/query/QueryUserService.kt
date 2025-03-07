package io.github.gdrfgdrf.cuteverification.web.services.impl.query

import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationSourceService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionTargetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class QueryUserService : IQueryService.IUserService {
    @Autowired
    private lateinit var identificationSourceService: IIdentificationSourceService
    @Autowired
    private lateinit var restrictionService: IRestrictionService
    @Autowired
    private lateinit var restrictionTargetService: IRestrictionTargetService

    @Transactional
    override fun identificationSources(userId: String, link: Boolean): List<IdentificationSource> {
        val list = identificationSourceService.ktQuery()
            .eq(IdentificationSource::userId, userId)
            .list()

        if (link) {
            identificationSourceService.autoMapper.mapperEntityList<IdentificationSource>(list)
        }

        return list
    }

    @Transactional
    override fun restrictions(userId: String, link: Boolean): List<Restriction> {
        val identificationIds = identificationSourceService.ktQuery()
            .select(IdentificationSource::identificationId, IdentificationSource::userId)
            .eq(IdentificationSource::userId, userId)
            .list()
            .toSet()
            .map {
                it.identificationId
            }
        if (identificationIds.isEmpty()) {
            return arrayListOf()
        }

        val restrictionIds = restrictionTargetService.ktQuery()
            .select(RestrictionTarget::restrictionId, RestrictionTarget::identificationId)
            .`in`(RestrictionTarget::identificationId, identificationIds)
            .list()
            .toSet()
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