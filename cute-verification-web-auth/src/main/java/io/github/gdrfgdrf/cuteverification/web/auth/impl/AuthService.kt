package io.github.gdrfgdrf.cuteverification.web.auth.impl

import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthService
import io.github.gdrfgdrf.cuteverification.web.auth.JwtUser
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAdministratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class AuthService : IAuthService {
    @Autowired
    private lateinit var administratorService: IAdministratorService

    override fun loadUserByUsername(username: String): UserDetails? {
        val administrator = administratorService.findByUsername(username)
        if (administrator == null) {
            throw UsernameNotFoundException("cannot find user by username: $username")
        }
        return JwtUser(administrator.username, administrator.password)
    }

    override fun authenticate(username: String, password: String): String {
        TODO("Not yet implemented")
    }

    override fun tokenExists(id: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun expireToken(id: String, duration: Duration) {
        TODO("Not yet implemented")
    }

    override fun removeToken(id: String) {
        TODO("Not yet implemented")
    }
}