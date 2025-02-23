package io.github.gdrfgdrf.cuteverification.web.advices

import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiResult
import io.github.gdrfgdrf.cuteverification.web.commons.result.ApiStatus
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(request: HttpServletRequest, e: Exception): ApiResult {
        return ApiStatus.FAIL.response()
    }

}