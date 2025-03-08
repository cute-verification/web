package io.github.gdrfgdrf.cuteverification.web.auth

import io.github.gdrfgdrf.cuteverification.web.commons.jwt.Jwts
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRedisService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAdministratorService
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Authenticator : IAuthenticator {
    @Autowired
    private lateinit var jwts: Jwts
    @Autowired
    private lateinit var authService: IAuthService
    @Autowired
    private lateinit var redisService: IRedisService
    @Autowired
    private lateinit var adminService: IAdministratorService

    override fun auth(token: String): Boolean {
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

    override fun id(username: String): String? {
        return adminService.findIdByUsername(username)
    }

    override fun username(token: String): String? {
        val decodedJwt = jwts.verify(token) ?: return null
        return decodedJwt.subject
    }

}