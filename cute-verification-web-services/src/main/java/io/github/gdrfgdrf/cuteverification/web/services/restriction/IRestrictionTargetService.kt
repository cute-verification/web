package io.github.gdrfgdrf.cuteverification.web.services.restriction

import com.github.dreamyoung.mprelation.IService
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget

interface IRestrictionTargetService : IService<RestrictionTarget> {
    fun create(restrictionId: String, identificationIds: List<String>)
}