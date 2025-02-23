package io.github.gdrfgdrf.cuteverification.web.services.impl.identification

import com.github.dreamyoung.mprelation.ServiceImpl
import io.github.gdrfgdrf.cuteverification.web.mappers.identification.IdentificationSourceMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationSourceService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class IdentificationSourceService : ServiceImpl<IdentificationSourceMapper, IdentificationSource>(), IIdentificationSourceService {
}