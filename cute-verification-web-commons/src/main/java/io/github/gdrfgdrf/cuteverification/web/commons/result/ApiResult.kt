package io.github.gdrfgdrf.cuteverification.web.commons.result

import io.github.gdrfgdrf.cuteverification.web.commons.json.Jsons
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType

data class ApiResult(
    var code: Int?,
    var success: Boolean?,
    var data: MutableMap<String, Any?>?
) {
    fun put(key: String, value: Any?): ApiResult {
        if (data == null) {
            data = mutableMapOf()
        }
        data!!.put(key, value)
        return this
    }

    fun writeTo(response: HttpServletResponse) {
        response.characterEncoding = Charsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(toString())
    }

    fun string(): String {
        return Jsons.write(this)
    }

    override fun toString(): String {
        return string()
    }

    companion object {
        fun response(
            code: Int,
            success: Boolean?,
            data: MutableMap<String, Any?>?
        ): ApiResult {
            return ApiResult(
                code,
                success,
                data
            )
        }

        fun response(
            code: Int,
            success: Boolean?,
            provider: (MutableMap<String, Any?>.() -> Unit)? = null
        ): ApiResult {
            val map = HashMap<String, Any?>()
            provider?.invoke(map)
            return response(code, success, map)
        }

        fun response(
            status: ApiStatus,
            data: MutableMap<String, Any?>?
        ): ApiResult {
            return ApiResult(
                status.code,
                status.success,
                data
            )
        }

        fun response(
            status: ApiStatus,
            provider: (MutableMap<String, Any?>.() -> Unit)? = null
        ): ApiResult {
            val map = HashMap<String, Any?>()
            provider?.invoke(map)
            return response(status, map)
        }

        fun success(
            data: MutableMap<String, Any?>? = null
        ): ApiResult {
            return response(ApiStatus.SUCCESS, data)
        }

        fun success(
            provider: (Map<String, Any?>.() -> Unit)? = null
        ): ApiResult {
            val map = HashMap<String, Any?>()
            provider?.invoke(map)
            return success(map)
        }

        fun fail(
            data: MutableMap<String, Any?>? = null
        ): ApiResult {
            return response(ApiStatus.FAIL, data)
        }

        fun fail(
            provider: (MutableMap<String, Any?>.() -> Unit)? = null
        ): ApiResult {
            val map = HashMap<String, Any?>()
            provider?.invoke(map)
            return fail(map)
        }
    }
}