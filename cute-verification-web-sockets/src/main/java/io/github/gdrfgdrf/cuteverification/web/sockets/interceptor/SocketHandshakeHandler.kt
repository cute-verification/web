package io.github.gdrfgdrf.cuteverification.web.sockets.interceptor

import cn.hutool.http.HttpUtil
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthenticator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeHandler

@Component
class SocketHandshakeHandler : HandshakeHandler {
    @Autowired
    private lateinit var authenticator: IAuthenticator

    override fun doHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>
    ): Boolean {
        val query = request.uri.query
        val params = HttpUtil.decodeParamMap(query, Charsets.UTF_8)

        if (!params.containsKey("token")) {
            return false
        }

        val token = params["token"]!!
        val available = authenticator.auth(token)
        if (!available) {
            return false
        }
        val username = authenticator.username(token)
        if (username.isNullOrBlank()) {
            return false
        }
        val id = authenticator.id(username)
        if (id == null) {
            return false
        }

        attributes["id"] = id
        attributes["username"] = username
        attributes["token"] = token

        return true
    }
}