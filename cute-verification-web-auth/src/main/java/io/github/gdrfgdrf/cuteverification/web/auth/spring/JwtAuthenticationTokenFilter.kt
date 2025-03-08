package io.github.gdrfgdrf.cuteverification.web.auth.spring

import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthenticator
import io.github.gdrfgdrf.cuteverification.web.interfaces.IJwtAuthenticationTokenFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationTokenFilter : IJwtAuthenticationTokenFilter() {
    @Autowired
    private lateinit var authenticator: IAuthenticator

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.getHeader("Authorization")
        if (token.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        if (SecurityContextHolder.getContext().authentication != null) {
            filterChain.doFilter(request, response)
            return
        }

        val available = authenticator.auth(token)
        if (!available) {
            filterChain.doFilter(request, response)
            return
        }
        val username = authenticator.username(token)
        if (username.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        val authentication = UsernamePasswordAuthenticationToken(
            username,
            null,
            arrayListOf<GrantedAuthority>()
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}