package io.github.gdrfgdrf.cuteverification.web.managers

import io.github.gdrfgdrf.cuteverification.abstracts.server.restriction.IRestriction

interface IRestrictionManager {
    fun existsById(id: String): Boolean

    fun findById(id: String): IRestriction

    fun create(restriction: IRestriction)
    fun deleteById(id: String)

    fun list(): List<IRestriction>
}