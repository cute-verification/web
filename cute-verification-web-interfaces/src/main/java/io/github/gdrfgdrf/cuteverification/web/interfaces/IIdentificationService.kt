package io.github.gdrfgdrf.cuteverification.web.interfaces

import com.github.dreamyoung.mprelation.IService
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.IdentificationPlatforms
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction

interface IIdentificationService : IService<Identification> {
    fun info(id: String): Identification?

    fun list(link: Boolean): List<Identification>
    fun listPage(page: Int, link: Boolean): PageInfo<Identification>?

    fun searchByCode(code: String, link: Boolean): PageInfo<Identification>?

    fun existsById(id: String): Boolean
    fun existsByCode(code: String): Boolean

    fun save(code: String, platform: IdentificationPlatforms, ip: String, userId: String)

    fun availableRestrictions(id: String, link: Boolean): List<Restriction>?
    fun expiredRestrictions(id: String, link: Boolean): List<Restriction>?
}