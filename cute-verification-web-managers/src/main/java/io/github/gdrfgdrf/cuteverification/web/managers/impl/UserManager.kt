package io.github.gdrfgdrf.cuteverification.web.managers.impl

import io.github.gdrfgdrf.cuteverification.abstracts.server.role.user.IUser
import io.github.gdrfgdrf.cuteverification.web.interfaces.IUserManager
import io.github.gdrfgdrf.cuteverification.web.pojo.role.user.User
import io.github.gdrfgdrf.cuteverification.web.interfaces.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class UserManager : IUserManager {
    @Autowired
    private lateinit var userService: IUserService

    override fun existsById(id: String): Boolean {
        val query = userService.ktQuery()
            .eq(User::id, id)
        return query.exists()
    }

    override fun existsByName(name: String): Boolean {
        val query = userService.ktQuery()
            .eq(User::username, name)
        return query.exists()
    }

    override fun findById(id: String): IUser {
        return userService.getById(id)
    }

    override fun findByName(name: String): IUser {
        val query = userService.ktQuery()
            .eq(User::username, name)
        return query.one()
    }

    override fun register(user: IUser) {
        userService.save(user as User)
    }

    override fun login(user: IUser) {
        user.lastLoginTime = Instant.now()
        userService.updateById(user as User?)
    }

    override fun list(): List<IUser> {
        return userService.ktQuery().list()
    }


}