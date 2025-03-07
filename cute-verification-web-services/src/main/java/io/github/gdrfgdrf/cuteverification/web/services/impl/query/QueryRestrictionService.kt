package io.github.gdrfgdrf.cuteverification.web.services.impl.query

import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationSourceService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionTargetService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class QueryRestrictionService : IQueryService.IRestrictionService {
    @Autowired
    private lateinit var restrictionTargetService: IRestrictionTargetService
    @Autowired
    private lateinit var identificationService: IIdentificationService
    @Autowired
    private lateinit var identificationSourceService: IIdentificationSourceService
    @Autowired
    private lateinit var userService: IUserService

    @Transactional
    override fun affectedIdentifications(restrictionId: String, link: Boolean): List<Identification> {
        val identificationIds = restrictionTargetService.ktQuery()
            .select(RestrictionTarget::identificationId, RestrictionTarget::restrictionId)
            .eq(RestrictionTarget::restrictionId, restrictionId)
            .list()
            .toSet()
            .map {
                it.identificationId
            }

        val identifications = identificationService.ktQuery()
            .`in`(Identification::id, identificationIds)
            .list()

        if (link) {
            identificationService.autoMapper.mapperEntityList<Identification>(identifications)
        }
        return identifications
    }

    @Transactional
    override fun affectedUsers(restrictionId: String): List<User> {
        val identificationIds = restrictionTargetService.ktQuery()
            .select(RestrictionTarget::identificationId, RestrictionTarget::restrictionId)
            .eq(RestrictionTarget::restrictionId, restrictionId)
            .list()
            .toSet()
            .map {
                it.identificationId
            }
        if (identificationIds.isEmpty()) {
            return arrayListOf()
        }

        val userIds = identificationSourceService.ktQuery()
            .select(IdentificationSource::identificationId, IdentificationSource::userId)
            .`in`(IdentificationSource::identificationId, identificationIds)
            .list()
            .toSet()
            .map {
                it.userId
            }
        if (userIds.isEmpty()) {
            return arrayListOf()
        }

        val users = userService.ktQuery()
            .`in`(User::id, userIds)
            .list()

        return users
    }
}