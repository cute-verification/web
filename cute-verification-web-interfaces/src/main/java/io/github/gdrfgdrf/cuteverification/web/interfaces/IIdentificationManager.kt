package io.github.gdrfgdrf.cuteverification.web.interfaces

import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentification
import io.github.gdrfgdrf.cuteverification.abstracts.server.identification.IIdentificationSource

interface IIdentificationManager {
    fun existsById(id: String): Boolean

    fun findById(id: String): IIdentification

    fun register(identification: IIdentification)
    fun addSource(identificationSource: IIdentificationSource)

    fun list(sources: Boolean): List<IIdentification>
    fun listSource(): List<IIdentificationSource>
}