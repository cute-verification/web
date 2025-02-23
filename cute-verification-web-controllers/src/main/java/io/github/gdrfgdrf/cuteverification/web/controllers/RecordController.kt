package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import io.github.gdrfgdrf.cuteverification.web.services.record.IRecordService
import jakarta.websocket.server.PathParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/records")
class RecordController {
    @Autowired
    private lateinit var recordService: IRecordService

    @RequestMapping(value = ["/list"], method = [RequestMethod.GET])
    fun listPage(@PathParam("page") page: Int?): ApiResult {
        if (page == null) {
            val list = recordService.ktQuery().list()
            return ApiStatus.FOUND.response {
                put("list", list)
            }
        }

        val pageInfo = recordService.listPage(page)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

    @RequestMapping(value = ["/search"], method = [RequestMethod.GET])
    fun search(@PathParam("message") message: String): ApiResult {
        val pageInfo = recordService.searchByMessage(message)
        if (pageInfo == null) {
            return ApiStatus.NOT_FOUND.response()
        }
        return ApiStatus.FOUND.response {
            put("page-info", pageInfo)
        }
    }

}