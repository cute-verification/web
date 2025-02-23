package io.github.gdrfgdrf.cuteverification.web.auth.spring.logout

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.services.record.IRecordService
import io.github.gdrfgdrf.cuteverification.web.services.redis.IRedisService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.stereotype.Component

@Component
class CustomLogoutSuccessHandler : LogoutSuccessHandler {
    @Autowired
    private lateinit var redisService: IRedisService
    @Autowired
    private lateinit var recordService: IRecordService

    override fun onLogoutSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication?
    ) {
        if (authentication == null) {
            ApiStatus.FAIL.response().writeTo(response)
            return
        }

        val name = authentication.name

        if (redisService.existsAccessTokenByName(name)) {
            redisService.removeAccessTokenByName(name)
        }

        ApiStatus.SUCCESS.response().writeTo(response)

        recordService.administratorLogout(name)
    }
}