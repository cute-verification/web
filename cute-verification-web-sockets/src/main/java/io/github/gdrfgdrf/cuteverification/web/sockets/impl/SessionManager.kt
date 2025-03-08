package io.github.gdrfgdrf.cuteverification.web.sockets.impl

import io.github.gdrfgdrf.cuteverification.web.auth.Authenticator
import io.github.gdrfgdrf.cuteverification.web.commons.json.Jsons
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.Session
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessage
import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessageTypes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionManager : ISessionManager {
    @Autowired
    lateinit var authenticator: Authenticator
    private val logonSessions = ConcurrentHashMap<String, Session>()

    override fun get(id: String): Session? {
        if (!logonSessions.containsKey(id)) {
            return null
        }
        return logonSessions.get(id)
    }

    override fun add(id: String, session: Session) {
        logonSessions[id] = session
    }

    override fun remove(id: String) {
        if (logonSessions.containsKey(id)) {
            val session = logonSessions[id]!!
            session.close()
        }

        logonSessions.remove(id)
    }

    override fun auth(session: Session): Boolean {
        return authenticator.auth(session.token)
    }

    override fun kick(id: String) {
        if (logonSessions.containsKey(id)) {
            val session = logonSessions[id]!!
            session.tokenInvalid()
        }

        logonSessions.remove(id)
    }

    override fun send(id: String, type: WsMessageTypes, provider: ((MutableMap<String, Any?>) -> Unit)?) {
        val session = get(id) ?: return
        val authResult = auth(session)
        if (!authResult) {
            session.tokenInvalid()
            return
        }

        WsMessage.of(type, provider).write(session)
    }

    override fun send(id: String, type: WsMessageTypes) {
        send(id, type)
    }




}