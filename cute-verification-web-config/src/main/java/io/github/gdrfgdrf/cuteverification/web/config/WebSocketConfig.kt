package io.github.gdrfgdrf.cuteverification.web.config

import io.github.gdrfgdrf.cuteverification.web.sockets.interceptor.SocketHandshakeInterceptor
import io.github.gdrfgdrf.cuteverification.web.sockets.handler.SocketMessageHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.server.standard.ServerEndpointExporter


@Configuration
@EnableWebSocket
open class WebSocketConfig : WebSocketConfigurer {
    @Autowired
    private lateinit var interceptor: SocketHandshakeInterceptor
    @Autowired
    private lateinit var messageHandler: SocketMessageHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(messageHandler, "/ws")
            .addInterceptors(interceptor)
            .setAllowedOrigins("*")
    }
}


