package io.github.gdrfgdrf.cuteverification.web.services.role.admin

import com.github.dreamyoung.mprelation.IService
import io.github.gdrfgdrf.cuteverification.abstracts.server.role.admin.IAdministrator
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.pojo.role.admin.Administrator

interface IAdministratorService : IService<Administrator> {
    fun findByUsername(username: String): Administrator?
    fun findIdByUsername(username: String): String?

    fun changePassword(username: String, old: String, new: String): Boolean
}