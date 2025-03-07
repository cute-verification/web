package io.github.gdrfgdrf.cuteverification.web.controllers.query

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/query/user/{user_id}")
class QueryUserController {
    @Autowired
    private lateinit var queryUserService: IQueryService.IUserService

    @RequestMapping(value = ["/identification/sources"], method = [RequestMethod.GET])
    fun identificationSources(@PathVariable("user_id") id: String, @PathParam("link") link: Boolean?): ApiResult {
        val list = queryUserService.identificationSources(id, link ?: false)
        return ApiStatus.FOUND.response {
            put("identification-sources", list)
        }
    }

    @RequestMapping(value = ["/restrictions"], method = [RequestMethod.GET])
    fun restrictions(@PathVariable("user_id") id: String, @PathParam("link") link: Boolean?): ApiResult {
        val list = queryUserService.restrictions(id, link ?: false)
        return ApiStatus.FOUND.response {
            put("restrictions", list)
        }
    }

}