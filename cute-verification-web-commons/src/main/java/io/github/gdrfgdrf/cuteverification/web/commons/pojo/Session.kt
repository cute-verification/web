package io.github.gdrfgdrf.cuteverification.web.commons.pojo

import org.springframework.web.socket.WebSocketSession

class Session(
    val id: String,
    val username: String,
    val token: String,
    val socketSession: WebSocketSession
) {
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