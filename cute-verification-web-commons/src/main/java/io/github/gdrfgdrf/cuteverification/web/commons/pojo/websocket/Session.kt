package io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket

import io.github.gdrfgdrf.cuteverification.web.commons.json.Jsons
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession

class Session(
    val id: String,
    val username: String,
    val token: String,
    val socketSession: WebSocketSession
) {
    fun tokenInvalid() {
        val message = WsMessage()
        message.type = WsMessageTypes.TOKEN_INVALID

        val content = Jsons.write(message)
        socketSession.sendMessage(TextMessage(content))

        socketSession.close()
    }

    fun close() {
        socketSession.close()
    }

    companion object {
        fun of(
            id: String,
            username: String,
            token: String,
            socketSession: WebSocketSession
        ): Session = Session(
            id,
            username,
            token,
            socketSession
        )
    }

}