package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.services.redis.IRedisService
import io.github.gdrfgdrf.cuteverification.web.services.role.admin.IAdministratorService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin")
class AdminController {
    @Autowired
    private lateinit var administratorService: IAdministratorService
    @Autowired
    private lateinit var redisService: IRedisService

    @RequestMapping("/password", method = [RequestMethod.PUT])
    fun changePassword(@PathParam("old") old: String, @PathParam("new") new: String): ApiResult {
        val username = SecurityContextHolder.getContext().authentication.name
        val result = administratorService.changePassword(username, old, new)
        if (result) {
            redisService.removeAccessTokenByName(username)
            return ApiStatus.SUCCESS.response()
        }
        return ApiStatus.FAIL.response()
    }

}