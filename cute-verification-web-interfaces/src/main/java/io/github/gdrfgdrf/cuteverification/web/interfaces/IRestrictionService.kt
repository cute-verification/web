package io.github.gdrfgdrf.cuteverification.web.interfaces

import com.github.dreamyoung.mprelation.IService
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.RestrictionCreationDTO
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction

interface IRestrictionService : IService<Restriction> {
    fun info(id: String): Restriction?

    fun list(link: Boolean): List<Restriction>
    fun listPage(page: Int, link: Boolean): PageInfo<Restriction>?

    fun existsById(id: String): Boolean

    fun create(restrictionCreationDto: RestrictionCreationDTO)
    fun delete(id: String)
    fun restore(id: String)
}