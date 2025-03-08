package io.github.gdrfgdrf.cuteverification.web.sockets.handler

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.Session
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class SocketMessageHandler : AbstractWebSocketHandler() {
    @Autowired
    private lateinit var sessionManager: ISessionManager

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val attributes = session.attributes
        val id = attributes["id"].toString()
        val username = attributes["username"].toString()
        val token = attributes["token"].toString()

        val session = Session.of(
            id,
            username,
            token,
            session
        )
        sessionManager.add(id, session)
    }

    override fun afterConnectionClosed(webSocketSession: WebSocketSession, status: CloseStatus) {
        val attributes = webSocketSession.attributes
        val id = attributes["id"].toString()
        sessionManager.remove(id)
    }
}