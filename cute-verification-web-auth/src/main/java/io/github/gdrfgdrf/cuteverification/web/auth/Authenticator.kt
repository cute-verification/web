package io.github.gdrfgdrf.cuteverification.web.auth

import io.github.gdrfgdrf.cuteverification.web.commons.jwt.Jwts
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRedisService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAdministratorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Authenticator {
    @Autowired
    private lateinit var jwts: Jwts
    @Autowired
    private lateinit var authService: IAuthService
    @Autowired
    private lateinit var redisService: IRedisService
    @Autowired
    private lateinit var adminService: IAdministratorService

    fun auth(token: String): Boolean {
        val decodedJwt = jwts.verify(token) ?: return false
        val usernameFromToken = decodedJwt.subject
        if (usernameFromToken.isNullOrBlank()) {
            return false
        }

        val administrator = runCatching {
            authService.loadUserByUsername(usernameFromToken)
        }.getOrNull()
        if (administrator == null) {
            return false
        }

        val tokenAvailable = redisService.existsAccessTokenByName(usernameFromToken)
        return tokenAvailable
    }

    fun id(username: String): String? {
        val administrator = adminService.findByUsername(username)
        if (administrator == null) {
            return null
        }
        return administrator.id
    }

    fun username(token: String): String? {
        val decodedJwt = jwts.verify(token) ?: return null
        return decodedJwt.subject
    }

}