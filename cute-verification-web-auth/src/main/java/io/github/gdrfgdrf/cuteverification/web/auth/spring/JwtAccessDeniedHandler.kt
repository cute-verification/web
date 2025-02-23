package io.github.gdrfgdrf.cuteverification.web.auth.spring

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException?
    ) {
        ApiStatus.FORBIDDEN.response().writeTo(response)
    }
}