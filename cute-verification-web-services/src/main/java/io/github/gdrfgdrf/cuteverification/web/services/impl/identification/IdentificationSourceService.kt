package io.github.gdrfgdrf.cuteverification.web.services.impl.identification

import com.github.dreamyoung.mprelation.ServiceImpl
import io.github.gdrfgdrf.cuteverification.web.mappers.identification.IdentificationSourceMapper
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.interfaces.IIdentificationSourceService
import org.springframework.stereotype.Service

@Service
open class IdentificationSourceService : ServiceImpl<IdentificationSourceMapper, IdentificationSource>(), IIdentificationSourceService {
}