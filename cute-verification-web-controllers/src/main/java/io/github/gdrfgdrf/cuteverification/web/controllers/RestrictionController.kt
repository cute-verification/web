package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.RestrictionCreationDTO
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.services.restriction.IRestrictionService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/restrictions")
class RestrictionController {
    @Autowired
    private lateinit var restrictionService: IRestrictionService

    @RequestMapping(value = ["/info"], method = [RequestMethod.GET])
    fun info(@PathParam("id") id: String): ApiResult {
        val result = restrictionService.info(id)
        if (result == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("restriction", result)
        }
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listPage(@PathParam("page") page: Int?, @PathParam("link") link: Boolean?): ApiResult {
        if (page == null) {
            val list = restrictionService.list(link ?: false)
            return ApiStatus.FOUND.response {
                put("list", list)
            }
        }

        val pageInfo = restrictionService.listPage(page, link ?: false)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/create"], method = [RequestMethod.POST])
    fun create(@RequestBody restrictionCreationDto: RestrictionCreationDTO): ApiResult {
        restrictionService.create(restrictionCreationDto)
        return ApiStatus.SUCCESS.response()
    }

    @RequestMapping(value = ["/delete"], method = [RequestMethod.DELETE])
    fun delete(@PathParam("id") id: String): ApiResult {
        restrictionService.delete(id)
        return ApiStatus.SUCCESS.response()
    }

    @RequestMapping(value = ["/restore"], method = [RequestMethod.PUT])
    fun restore(@PathParam("id") id: String): ApiResult {
        restrictionService.restore(id)
        return ApiStatus.SUCCESS.response()
    }

}