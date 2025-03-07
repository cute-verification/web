package io.github.gdrfgdrf.cuteverification.web.services.impl.redis

import io.github.gdrfgdrf.cuteverification.web.interfaces.IRedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RedisService : IRedisService {
    private val keyPrefix = "token:"
    @Autowired
    private lateinit var redis: RedisTemplate<String, String>

    override fun existsAccessTokenByName(name: String): Boolean {
        return findAccessTokenByName(name) != null
    }

    override fun findAccessTokenByName(name: String): String? {
        val keys = redis.keys("$keyPrefix*")
        keys.forEach { key ->
            if (redis.opsForValue().get(key) == name) {
                return key.removePrefix(keyPrefix)
            }
        }
        return null
    }

    override fun removeAccessTokenByName(name: String) {
        findAccessTokenByName(name)?.let {
            removeNameByAccessToken(it)
        }
    }

    override fun existsNameByAccessToken(accessToken: String): Boolean {
        return findNameByAccessToken(accessToken) != null
    }

    override fun findNameByAccessToken(accessToken: String): String? {
        return redis.opsForValue().get(keyPrefix + accessToken)
    }

    override fun removeNameByAccessToken(accessToken: String) {
        redis.delete(keyPrefix + accessToken)
    }

    override fun addAccessToken(accessToken: String, name: String, expire: Duration) {
        redis.opsForValue().set(keyPrefix + accessToken, name, expire)
    }


}