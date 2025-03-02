package io.github.gdrfgdrf.cuteverification.web.services.impl.identification

import com.github.dreamyoung.mprelation.ServiceImpl
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.IdentificationPlatforms
import io.github.gdrfgdrf.cuteverification.web.mappers.identification.IdentificationMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationService
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationSourceService
import io.github.gdrfgdrf.cuteverification.web.services.query.IQueryService
import io.github.gdrfgdrf.cuteverification.web.services.restriction.IRestrictionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
open class IdentificationService : ServiceImpl<IdentificationMapper, Identification>(), IIdentificationService {
    @Autowired
    private lateinit var sourceService: IIdentificationSourceService
    @Autowired
    @Lazy
    private lateinit var queryIdentificationService: IQueryService.IIdentificationService
    @Autowired
    @Lazy
    private lateinit var restrictionService: IRestrictionService

    @Transactional
    override fun info(id: String): Identification? {
        val result = ktQuery().eq(Identification::id, id).one()
        if (result != null) {
            autoMapper.mapperEntity<Identification>(result)
            sourceService.autoMapper.mapperEntityList<IdentificationSource>(result.sources)
        }

        return result
    }

    @Transactional
    override fun list(link: Boolean): List<Identification> {
        val list = ktQuery().list()
        if (link) {
            autoMapper.mapperEntityList<Identification>(list)
        }

        return list
    }

    @Transactional
    override fun listPage(page: Int, link: Boolean): PageInfo<Identification>? {
        if (page <= 0) {
            return null
        }

        PageHelper.startPage<Identification>(page, 10)
        val list = ktQuery().list()
        val pageInfo = PageInfo(list)
        if (link) {
            autoMapper.mapperEntityList<Identification>(pageInfo.list)
        }

        return pageInfo
    }

    @Transactional
    override fun searchByCode(code: String, link: Boolean): PageInfo<Identification>? {
        if (code.trim().isBlank()) {
            return null
        }

        val list = ktQuery().like(Identification::code, code).list()
        if (link) {
            autoMapper.mapperEntityList<Identification>(list)
        }

        return PageInfo(list)
    }

    override fun existsById(id: String): Boolean {
        val query = ktQuery()
            .eq(Identification::id, id)
        return query.exists()
    }

    @Transactional
    override fun existsByCode(code: String): Boolean {
        val query = ktQuery()
            .eq(Identification::code, code)
        return query.exists()
    }

    @Transactional
    override fun save(code: String, platform: IdentificationPlatforms, ip: String, userId: String) {
        val identification: Identification?

        if (!existsByCode(code)) {
            identification = Identification()
            identification.code = code
            save(identification)
        } else {
            val query = ktQuery()
                .eq(Identification::code, code)
            identification = query.one()
            if (identification == null) {
                throw IllegalArgumentException("cannot find any identifications by code $code")
            }
        }

        val source = IdentificationSource()
        source.userId = userId
        source.ip = ip
        source.time = Instant.now()
        source.platform = platform
        source.identificationId = identification.id

        sourceService.save(source)
    }

    override fun availableRestrictions(id: String, link: Boolean): List<Restriction>? {
        if (!existsById(id)) {
            return null
        }

        val now = Instant.now()
        val result = arrayListOf<Restriction>()

        val restrictions = queryIdentificationService.restrictions(id, false)
        restrictions.forEach { restriction ->
            val startAt = restriction.startAt
            val expireAt = restriction.expireAt

            if (now >= startAt && now <= expireAt) {
                result.add(restriction)
            }
        }

        if (link) {
            restrictionService.autoMapper.mapperEntityList<Restriction>(result)
        }

        return result
    }

    override fun expiredRestrictions(id: String, link: Boolean): List<Restriction>? {
        if (!existsById(id)) {
            return null
        }

        val now = Instant.now()
        val result = arrayListOf<Restriction>()

        val restrictions = queryIdentificationService.restrictions(id, false)
        restrictions.forEach { restriction ->
            val startAt = restriction.startAt
            val expireAt = restriction.expireAt

            if (now >= startAt && now >= expireAt) {
                result.add(restriction)
            }
        }

        if (link) {
            restrictionService.autoMapper.mapperEntityList<Restriction>(result)
        }

        return result
    }
}