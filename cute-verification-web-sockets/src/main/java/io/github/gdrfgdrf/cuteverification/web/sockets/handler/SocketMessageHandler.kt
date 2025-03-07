package io.github.gdrfgdrf.cuteverification.web.sockets.handler

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.Session
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import io.github.gdrfgdrf.cuteverification.web.sockets.impl.SessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class SocketMessageHandler : TextWebSocketHandler() {
    @Autowired
    private lateinit var sessionManager: ISessionManager

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val attributes = session.attributes
        val id = attributes["id"].toString()
        val username = attributes["username"].toString()
        val token = attributes["token"].toString()

        val session = Session.Companion.of(
            id,
            username,
            token,
            session
        )
        sessionManager.add(id, session)
    }

    override fun handleMessage(webSocketSession: WebSocketSession, message: WebSocketMessage<*>) {
        val attributes = webSocketSession.attributes
        val id = attributes["id"].toString()
        val session = sessionManager.get(id)
        if (session == null) {
            webSocketSession.close()
            return
        }
        val available = sessionManager.auth(session)
        if (!available) {
            session.close()
            return
        }

        super.handleMessage(webSocketSession, message)
    }

    override fun afterConnectionClosed(webSocketSession: WebSocketSession, status: CloseStatus) {
        val attributes = webSocketSession.attributes
        val id = attributes["id"].toString()
        sessionManager.remove(id)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {

    }


}