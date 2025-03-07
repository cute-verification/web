package io.github.gdrfgdrf.cuteverification.web.controllers.query

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.interfaces.IQueryService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/query/restriction/{restriction_id}")
class QueryRestrictionController {
    @Autowired
    private lateinit var queryRestrictionService: IQueryService.IRestrictionService

    @RequestMapping(value = ["/identifications"])
    fun affectedIdentifications(@PathVariable("restriction_id") id: String, @PathParam("link") link: Boolean?): ApiResult {
        val list = queryRestrictionService.affectedIdentifications(id, link ?: false)
        return ApiStatus.FOUND.response {
            put("identifications", list)
        }
    }

    @RequestMapping(value = ["/users"])
    fun affectedUsers(@PathVariable("restriction_id") id: String): ApiResult {
        val list = queryRestrictionService.affectedUsers(id)
        return ApiStatus.FOUND.response {
            put("users", list)
        }
    }

}