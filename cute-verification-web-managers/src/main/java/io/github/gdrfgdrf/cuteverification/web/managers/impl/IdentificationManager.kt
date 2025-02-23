package io.github.gdrfgdrf.cuteverification.web.managers.impl

import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentificationSource
import io.github.gdrfgdrf.cuteverification.web.managers.IIdentificationManager
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationService
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationSourceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IdentificationManager : IIdentificationManager {
    @Autowired
    private lateinit var identificationService: IIdentificationService
    @Autowired
    private lateinit var identificationSourceService: IIdentificationSourceService

    override fun existsById(id: String): Boolean {
        val query = identificationService.ktQuery()
            .eq(Identification::id, id)
        return identificationService.exists(query)
    }

    override fun findById(id: String): IIdentification {
        return identificationService.getById(id)
    }

    override fun register(identification: IIdentification) {
        identificationService.save(identification as Identification)
    }

    override fun addSource(identificationSource: IIdentificationSource) {
        identificationSourceService.save(identificationSource as IdentificationSource)
    }

    override fun list(sources: Boolean): List<IIdentification> {
        return identificationService.list(sources)
    }

    override fun listSource(): List<IIdentificationSource> {
        return identificationSourceService.ktQuery().list()
    }
}