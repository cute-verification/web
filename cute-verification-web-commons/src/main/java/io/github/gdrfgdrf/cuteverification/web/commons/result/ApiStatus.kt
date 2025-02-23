package io.github.gdrfgdrf.cuteverification.web.commons.result

enum class ApiStatus(val code: Int, val success: Boolean) {
    SUCCESS(100, true),
    FAIL(101, false),

    ALREADY_EXIST(102, false),

    NOT_FOUND(103, false),
    FOUND(104, true),

    UNAUTHORIZED(105, false),
    FORBIDDEN(106, false),

    ;

    fun response(provider: (MutableMap<String, Any?>.() -> Unit)? = null): ApiResult {
        return ApiResult.Companion.response(this, provider)
    }
}