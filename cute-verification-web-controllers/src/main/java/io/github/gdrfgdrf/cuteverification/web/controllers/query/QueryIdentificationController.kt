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
@RequestMapping("/api/v1/query/identification/{identification_id}")
class QueryIdentificationController {
    @Autowired
    private lateinit var queryIdentificationService: IQueryService.IIdentificationService

    @RequestMapping(value = ["/restrictions"], method = [RequestMethod.GET])
    fun restrictions(@PathVariable("identification_id") id: String, @PathParam("link") link: Boolean?): ApiResult {
        val list = queryIdentificationService.restrictions(id, link ?: false)
        return ApiStatus.FOUND.response {
            put("restrictions", list)
        }

    }

}