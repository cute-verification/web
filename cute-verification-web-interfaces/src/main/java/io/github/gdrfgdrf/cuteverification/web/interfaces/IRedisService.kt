package io.github.gdrfgdrf.cuteverification.web.interfaces

import java.time.Duration

interface IRedisService {
    fun existsAccessTokenByName(name: String): Boolean
    fun findAccessTokenByName(name: String): String?
    fun removeAccessTokenByName(name: String)

    fun existsNameByAccessToken(accessToken: String): Boolean
    fun findNameByAccessToken(accessToken: String): String?
    fun removeNameByAccessToken(accessToken: String)

    fun addAccessToken(accessToken: String, name: String, expire: Duration)
}