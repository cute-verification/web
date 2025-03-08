package io.github.gdrfgdrf.cuteverification.web.services.impl.restriction

import com.github.dreamyoung.mprelation.ServiceImpl
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.RestrictionCreationDTO
import io.github.gdrfgdrf.cuteverification.web.mappers.restriction.RestrictionMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRecordService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionTargetService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAdministratorService
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
open class RestrictionService : ServiceImpl<RestrictionMapper, Restriction>(), IRestrictionService {
    @Autowired
    private lateinit var administratorService: IAdministratorService
    @Autowired
    @Lazy
    private lateinit var restrictionTargetService: IRestrictionTargetService
    @Autowired
    private lateinit var recordService: IRecordService
    @Autowired
    private lateinit var sessionSender: ISessionSender

    @Transactional
    override fun info(id: String): Restriction? {
        val restriction = ktQuery().eq(Restriction::id, id).one()
        if (restriction != null) {
            autoMapper.mapperEntity(restriction)
        }
        return restriction
    }

    @Transactional
    override fun list(link: Boolean): List<Restriction> {
        val list = ktQuery().list()
        if (link) {
            autoMapper.mapperEntityList(list)
        }
        return list
    }

    @Transactional
    override fun listPage(page: Int, link: Boolean): PageInfo<Restriction>? {
        if (page <= 0) {
            return null
        }

        PageHelper.startPage<Restriction>(page, 10)
        val list = ktQuery().list()
        val pageInfo = PageInfo(list)
        if (link) {
            autoMapper.mapperEntityList<Restriction>(pageInfo.list)
        }

        return pageInfo
    }

    @Transactional
    override fun existsById(id: String): Boolean {
        val query = ktQuery()
            .eq(Restriction::id, id)
        return query.exists()
    }

    @Transactional
    override fun create(restrictionCreationDto: RestrictionCreationDTO) {
        val now = Instant.now()
        val startAt = restrictionCreationDto.buildStartAt()
        val expireAt = restrictionCreationDto.buildExpireAt()

        if (expireAt <= startAt) {
            throw IllegalArgumentException("expire time cannot lower than or equal to start time")
        }

        val list = restrictionCreationDto.targets
        if (list.isEmpty()) {
            throw IllegalArgumentException("target list is empty")
        }

        val username = SecurityContextHolder.getContext().authentication.name
        val id = administratorService.findIdByUsername(username)
        if (id == null) {
            throw IllegalArgumentException()
        }

        val restriction = Restriction()
        restriction.createAt = now
        restriction.startAt = startAt
        restriction.expireAt = expireAt
        restriction.administratorId = id
        restriction.deleted = false

        save(restriction)

        if (restriction.id == null) {
            throw IllegalArgumentException()
        }
        restrictionTargetService.create(restriction.id!!, list)

        recordService.createRestriction(restriction.id!!)
    }

    @Transactional
    override fun delete(id: String) {
        if (!existsById(id)) {
            throw IllegalArgumentException("unknown restriction id: $id")
        }

        val restriction = ktQuery()
            .eq(Restriction::id, id)
            .one()
        restriction.deleted = true

        updateById(restriction)

        recordService.deleteRestriction(id)
    }

    @Transactional
    override fun restore(id: String) {
        if (!existsById(id)) {
            throw IllegalArgumentException("unknown restriction id: $id")
        }

        val restriction = ktQuery()
            .eq(Restriction::id, id)
            .one()
        restriction.deleted = false

        updateById(restriction)

        recordService.restoreRestriction(id)
    }
}