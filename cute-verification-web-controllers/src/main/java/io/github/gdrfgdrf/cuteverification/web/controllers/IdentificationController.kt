package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.managers.IIdentificationManager
import io.github.gdrfgdrf.cuteverification.web.pojo.identification.Identification
import io.github.gdrfgdrf.cuteverification.web.pojo.restriction.Restriction
import io.github.gdrfgdrf.cuteverification.web.services.identification.IIdentificationService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/api/v1/identifications"])
class IdentificationController {
    @Autowired
    private lateinit var identificationService: IIdentificationService

    @RequestMapping(value = ["/info"], method = [RequestMethod.GET])
    fun info(@PathParam("id") id: String): ApiResult {
        val result = identificationService.info(id)
        if (result == null) {
            return ApiStatus.NOT_FOUND.response()
        }

        return ApiStatus.FOUND.response {
            put("identification", result)
        }
    }

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun list(@PathParam("page") page: Int?, @PathParam("link") link: Boolean?): ApiResult {
        if (page == null) {
            val list = identificationService.list(link ?: false)
            return ApiStatus.FOUND.response {
                put("list", list)
            }
        }

        val pageInfo = identificationService.listPage(page, link ?: false)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }

        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/search"], method = [RequestMethod.GET])
    fun search(@PathParam("code") code: String, @PathParam("sources") sources: Boolean?): ApiResult {
        val pageInfo = identificationService.searchByCode(code, sources ?: false)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/restrictions"], method = [RequestMethod.GET])
    fun restrictions(
        @PathParam("id") id: String,
        @PathParam("link") link: Boolean?,
        @PathParam("status") status: String
    ) : ApiResult {
        if (status != "available" && status != "expired") {
            return ApiStatus.FAIL.response()
        }

        var result: List<Restriction>? = null

        when (status) {
            "available" -> {
                result = identificationService.availableRestrictions(id, link ?: false)
            }
            "expired" -> {
                val expiredRestrictions = identificationService.expiredRestrictions(id, link ?: false)
                result = expiredRestrictions
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
        val availableRestrictions = identificationService.availableRestrictions(id, false)
        return ApiStatus.SUCCESS.response {
            put("restricted", !availableRestrictions.isNullOrEmpty())
        }
    }

}