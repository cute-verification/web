package io.github.gdrfgdrf.cuteverification.web.controllers

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.ServerInfo
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/v1/info")
class InfoController {
    @Value("\${server.info.name}")
    private lateinit var name: String

    @RequestMapping("/server", method = [RequestMethod.GET])
    fun info(): ApiResult {
        val serverInfo = ServerInfo.collect()
        serverInfo.name = name
        serverInfo.version = "0.0.1"

        return ApiStatus.SUCCESS.response {
            put("info", serverInfo)
        }
    }


}