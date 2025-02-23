package io.github.gdrfgdrf.cuteverification.web.auth

import org.springframework.security.core.userdetails.UserDetailsService
import java.time.Duration

interface IAuthService : UserDetailsService {
    fun authenticate(username: String, password: String): String

    fun tokenExists(id: String): Boolean
    fun expireToken(id: String, duration: Duration)
    fun removeToken(id: String)
}