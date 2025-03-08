package io.github.gdrfgdrf.cuteverification.web.config

import io.github.gdrfgdrf.cuteverification.web.sockets.handler.SocketMessageHandler
import io.github.gdrfgdrf.cuteverification.web.sockets.interceptor.SocketHandshakeInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
open class WebSocketConfig : WebSocketConfigurer {
    @Autowired
    private lateinit var handshakeHandler: SocketHandshakeInterceptor
    @Autowired
    private lateinit var messageHandler: SocketMessageHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(messageHandler, "/websocket")
            .addInterceptors(handshakeHandler)
            .setAllowedOriginPatterns("*")
            .withSockJS()
        registry.addHandler(messageHandler, "/websocket/no-sock-js")
            .addInterceptors(handshakeHandler)
            .setAllowedOriginPatterns("*")
    }
}


