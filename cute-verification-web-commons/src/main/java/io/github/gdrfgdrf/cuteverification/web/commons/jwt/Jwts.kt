package io.github.gdrfgdrf.cuteverification.web.commons.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class Jwts {
    @Value("\${config.token.expire-after}")
    private var expireAfter: Long = -1
    @Value("\${config.token.signature-key}")
    private lateinit var signatureKey: String

    fun build(username: String): String {
        return build(username, expireAt())
    }

    fun build(username: String, expireAt: Date): String {
        val nowMillis = System.currentTimeMillis()
        val now = Date(nowMillis)
        return JWT.create()
            .withSubject(username)
            .withExpiresAt(expireAt)
            .withIssuedAt(now)
            .sign(algorithm())
    }

    fun verifier(): JWTVerifier {
        return JWT.require(algorithm()).build()
    }

    fun verify(token: String): DecodedJWT? {
        return verify(verifier(), token)
    }

    fun verify(jwtVerifier: JWTVerifier, token: String): DecodedJWT? {
        runCatching {
            return jwtVerifier.verify(token)
        }.onFailure {
            return null
        }
        return null
    }

    fun verifyName(token: String, targetName: String): Boolean {
        val decodedJWT = verify(token) ?: return false
        val username = decodedJWT.subject
        return username == targetName
    }

    private fun expireAt(): Date {
        return Date(System.currentTimeMillis() + expireAfter)
    }

    private fun algorithm(): Algorithm {
        return Algorithm.HMAC256(signatureKey)
    }
}