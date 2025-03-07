package io.github.gdrfgdrf.cuteverification.web.interfaces

import com.github.dreamyoung.mprelation.IService
import io.github.gdrfgdrf.cuteverification.web.pojo.role.admin.Administrator

interface IAdministratorService : IService<Administrator> {
    fun findByUsername(username: String): Administrator?
    fun findIdByUsername(username: String): String?

    fun changePassword(username: String, old: String, new: String): Boolean
}