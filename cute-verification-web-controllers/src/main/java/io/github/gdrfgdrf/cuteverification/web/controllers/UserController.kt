package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.UserLoginDTO
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.managers.IUserManager
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.services.role.user.IUserService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.StopWatch
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit

@RestController
@RequestMapping("/api/v1/users")
class UserController {
    @Autowired
    private lateinit var userService: IUserService

    @RequestMapping(value = ["info"], method = [RequestMethod.GET])
    fun info(@PathParam("id") id: String): ApiResult {
        val result = userService.info(id)
        if (result == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("user", result)
        }
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listPage(@PathParam("page") page: Int?): ApiResult {
        if (page == null) {
            val list = userService.ktQuery().list()
            return ApiStatus.FOUND.response {
                put("list", list)
            }
        }

        val pageInfo = userService.listPage(page)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/search"], method = [RequestMethod.GET])
    fun search(@PathParam("username") username: String): ApiResult {
        val pageInfo = userService.searchByUsername(username)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/exists"], method = [RequestMethod.GET])
    fun existsById(@PathParam("id") id: String): ApiResult {
        val result = userService.existsById(id)
        return if (result) {
            ApiStatus.FOUND.response()
        } else {
            ApiStatus.NOT_FOUND.response()
        }
    }

    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody userLoginDto: UserLoginDTO): ApiResult {
        val user = userService.login(userLoginDto)
        return ApiStatus.SUCCESS.response {
            put("username", user.username)
            put("user-id", user.id)
        }
    }

    @RequestMapping(value = ["/restrictions"], method = [RequestMethod.GET])
    fun restrictions(
        @PathParam("id") id: String,
        @PathParam("link") link: Boolean?,
        @PathParam("status") status: String
    ): ApiResult {
        if (status != "available" && status != "expired") {
            return ApiStatus.FAIL.response()
        }

        var result: List<Restriction>? = null

        when (status) {
            "available" -> {
                result = userService.availableRestrictions(id, link ?: false)
            }
            "expired" -> {
                result = userService.expiredRestrictions(id, link ?: false)
            }
        }

        if (result.isNullOrEmpty()) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("restrictions", result)
        }
    }

    @RequestMapping(value = ["/restricted"], method = [RequestMethod.GET])
    fun restricted(@PathParam("id") id: String): ApiResult {
        val availableRestrictions = userService.availableRestrictions(id, false)
        return ApiStatus.SUCCESS.response {
            put("restricted", !availableRestrictions.isNullOrEmpty())
        }
    }

}