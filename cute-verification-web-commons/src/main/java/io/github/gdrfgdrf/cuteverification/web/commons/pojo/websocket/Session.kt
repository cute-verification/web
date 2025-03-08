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
        WsMessage.of(WsMessageTypes.TOKEN_INVALID)
            .write(this)
        close()
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