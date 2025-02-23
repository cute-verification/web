package io.github.gdrfgdrf.cuteverification.web.auth.spring.login

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class LoginFailureHandler : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        ApiStatus.FAIL.response().writeTo(response)
    }
}