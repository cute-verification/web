package io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket

import io.github.gdrfgdrf.cuteverification.web.commons.json.Jsons
import org.springframework.web.socket.TextMessage

data class WsMessage(
    var type: WsMessageTypes?,
    var data: MutableMap<String, Any?>?
) {
    fun put(key: String, value: Any?): WsMessage {
        if (data == null) {
            data = mutableMapOf()
        }
        data!!.put(key, value)
        return this
    }

    fun write(session: Session) {
        session.socketSession.sendMessage(TextMessage(string()))
    }

    fun string(): String {
        return Jsons.write(this)
    }

    companion object {
        fun of(
            type: WsMessageTypes,
            provider: (MutableMap<String, Any?>.() -> Unit)? = null
        ): WsMessage {
            val map = HashMap<String, Any?>()
            provider?.invoke(map)
            return WsMessage(type, map)
        }
    }
}