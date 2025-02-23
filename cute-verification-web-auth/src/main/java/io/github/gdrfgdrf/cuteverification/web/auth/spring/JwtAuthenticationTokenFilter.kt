package io.github.gdrfgdrf.cuteverification.web.auth.spring

import io.github.gdrfgdrf.cuteverification.web.auth.IAuthService
import io.github.gdrfgdrf.cuteverification.web.commons.jwt.Jwts
import io.github.gdrfgdrf.cuteverification.web.services.redis.IRedisService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationTokenFilter : OncePerRequestFilter() {
    @Autowired
    private lateinit var authService: IAuthService
    @Autowired
    private lateinit var redisService: IRedisService
    @Autowired
    private lateinit var jwts: Jwts

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

        val decodedJwt = jwts.verify(token)
        if (decodedJwt == null) {
            filterChain.doFilter(request, response)
            return
        }

        val usernameFromToken = decodedJwt.subject
        if (usernameFromToken.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        val administrator = runCatching {
            authService.loadUserByUsername(usernameFromToken)
        }.getOrNull()
        if (administrator == null) {
            filterChain.doFilter(request, response)
            return
        }

        val usernameFromDatabase = administrator.username
        if (usernameFromDatabase.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        val usernameFromRedis = redisService.findNameByAccessToken(token)
        if (usernameFromRedis.isNullOrBlank()) {
            filterChain.doFilter(request, response)
            return
        }

        if (usernameFromToken == usernameFromDatabase && usernameFromToken == usernameFromRedis) {
            val authentication = UsernamePasswordAuthenticationToken(
                usernameFromDatabase,
                null,
                administrator.authorities
            )
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}