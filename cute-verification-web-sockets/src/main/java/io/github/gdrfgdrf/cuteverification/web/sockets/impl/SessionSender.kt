package io.github.gdrfgdrf.cuteverification.web.sockets.impl

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessage
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessageTypes
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SessionSender : ISessionSender {
    @Autowired
    private lateinit var sessionManager: ISessionManager

    override fun send(id: String, type: WsMessageTypes, provider: (MutableMap<String, Any?>.() -> Unit)?) {
        val session = sessionManager.get(id) ?: return
        val authResult = sessionManager.auth(session)
        if (!authResult) {
            session.tokenInvalid()
            return
        }

        WsMessage.of(type, provider).write(session)
    }

    override fun send(id: String, type: WsMessageTypes) {
        send(id, type, null)
    }

    override fun broadcast(type: WsMessageTypes, provider: MutableMap<String, Any?>.() -> Unit) {
        sessionManager.sessions().forEach { session ->
            val id = session.id
            send(id, type, provider)
        }
    }

    override fun broadcast(type: WsMessageTypes) {
        sessionManager.sessions().forEach { session ->
            val id = session.id
            send(id, type)
        }
    }

    override fun restrictionCreated(restrictionId: String) {
        broadcast(WsMessageTypes.RESTRICTION_CREATED) {
            put("restriction-id", restrictionId)
        }
    }

    override fun restrictionRestored(restrictionId: String) {
        broadcast(WsMessageTypes.RESTRICTION_RESTORED) {
            put("restriction-id", restrictionId)
        }
    }

    override fun restrictionStarted(restrictionIds: List<String>) {
        broadcast(WsMessageTypes.RESTRICTION_STARTED) {
            put("restriction-ids", restrictionIds)
        }
    }
}