package io.github.gdrfgdrf.cuteverification.web.config

import io.github.gdrfgdrf.cuteverification.web.sockets.handler.SocketMessageHandler
import io.github.gdrfgdrf.cuteverification.web.sockets.interceptor.SocketHandshakeHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
open class WebSocketConfig : WebSocketConfigurer {
    @Autowired
    private lateinit var handshakeHandler: SocketHandshakeHandler
    @Autowired
    private lateinit var messageHandler: SocketMessageHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(messageHandler, "/websocket")
            .setHandshakeHandler(handshakeHandler)
            .setAllowedOriginPatterns("*")
            .withSockJS()
        registry.addHandler(messageHandler, "/websocket/no-sock-js")
            .setHandshakeHandler(handshakeHandler)
            .setAllowedOriginPatterns("*")
    }
}


