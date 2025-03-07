package io.github.gdrfgdrf.cuteverification.web.interfaces

import com.github.dreamyoung.mprelation.IService
import com.github.pagehelper.PageInfo
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.UserLoginDTO
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User

interface IUserService : IService<User> {
    fun info(id: String): User?

    fun listPage(page: Int): PageInfo<User>?
    fun searchByUsername(username: String): PageInfo<User>?

    fun existsById(id: String): Boolean
    fun existsByUsername(username: String): Boolean

    fun login(userLoginDto: UserLoginDTO): User

    fun availableRestrictions(id: String, link: Boolean): List<Restriction>?
    fun expiredRestrictions(id: String, link: Boolean): List<Restriction>?
}