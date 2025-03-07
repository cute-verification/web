package io.github.gdrfgdrf.cuteverification.web.interfaces

import io.github.gdrfgdrf.cuteverification.abstracts.server.role.user.IUser

interface IUserManager {
    fun existsById(id: String): Boolean
    fun existsByName(name: String): Boolean

    fun findById(id: String): IUser
    fun findByName(name: String): IUser

    fun register(user: IUser)
    fun login(user: IUser)

    fun list(): List<IUser>
}