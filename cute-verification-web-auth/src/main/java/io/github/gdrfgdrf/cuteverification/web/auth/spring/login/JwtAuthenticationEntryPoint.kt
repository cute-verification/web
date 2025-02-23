package io.github.gdrfgdrf.cuteverification.web.auth.spring.login

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        ApiStatus.UNAUTHORIZED.response().writeTo(response)
    }
}