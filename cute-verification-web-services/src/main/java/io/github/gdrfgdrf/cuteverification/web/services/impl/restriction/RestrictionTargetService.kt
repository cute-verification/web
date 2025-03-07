package io.github.gdrfgdrf.cuteverification.web.services.impl.restriction

import com.github.dreamyoung.mprelation.ServiceImpl
import io.github.gdrfgdrf.cuteverification.web.mappers.restriction.RestrictionTargetMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.RestrictionTarget
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionTargetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RestrictionTargetService : ServiceImpl<RestrictionTargetMapper, RestrictionTarget>(), IRestrictionTargetService {
    @Autowired
    private lateinit var identificationService: IIdentificationService

    @Transactional
    override fun create(restrictionId: String, identificationIds: List<String>) {
        val results = arrayListOf<RestrictionTarget>()

        identificationIds.forEach { identificationId ->
            if (!identificationService.existsById(identificationId)) {
                throw IllegalArgumentException("unknown identification id: $identificationId")
            }

            val restrictionTarget = RestrictionTarget()
            restrictionTarget.restrictionId = restrictionId
            restrictionTarget.identificationId = identificationId
            results.add(restrictionTarget)
        }

        saveBatch(results)
    }
}