package io.github.gdrfgdrf.cuteverification.web.auth.spring.login

import io.github.gdrfgdrf.cuteverification.web.commons.jwt.Jwts
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.services.record.IRecordService
import io.github.gdrfgdrf.cuteverification.web.services.redis.IRedisService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class JwtAuthenticationSuccessHandler : AuthenticationSuccessHandler {
    @Autowired
    private lateinit var jwts: Jwts
    @Autowired
    private lateinit var redisService: IRedisService
    @Value("\${config.token.redis-expire-after}")
    private var redisExpireAfter: Long = -1
    @Autowired
    private lateinit var recordService: IRecordService

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val name = authentication.name
        val token = jwts.build(name)

        val exists = redisService.existsAccessTokenByName(name)
        if (exists) {
            redisService.removeAccessTokenByName(name)
        }
        redisService.addAccessToken(token, name, Duration.ofMillis(redisExpireAfter))

        ApiStatus.SUCCESS.response {
            put("token", token)
        }.writeTo(response)

        SecurityContextHolder.getContext().authentication = authentication

        recordService.administratorLogin()
    }
}