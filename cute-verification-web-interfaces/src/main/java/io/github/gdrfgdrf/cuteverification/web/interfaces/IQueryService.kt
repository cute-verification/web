package io.github.gdrfgdrf.cuteverification.web.interfaces

import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.IdentificationSource
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User

interface IQueryService {

    interface IUserService {
        fun identificationSources(userId: String, link: Boolean): List<IdentificationSource>
        fun restrictions(userId: String, link: Boolean): List<Restriction>
    }

    interface IRestrictionService {
        fun affectedIdentifications(restrictionId: String, link: Boolean): List<Identification>
        fun affectedUsers(restrictionId: String): List<User>
    }

    interface IIdentificationService {
        fun restrictions(identificationId: String, link: Boolean): List<Restriction>
    }

}