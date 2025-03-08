package io.github.gdrfgdrf.cuteverification.web.sockets.impl

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.Session
import io.github.gdrfgdrf.cuteverification.web.interfaces.IAuthenticator
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class SessionManager : ISessionManager {
    @Autowired
    lateinit var authenticator: IAuthenticator
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

    override fun sessions(): List<Session> {
        return logonSessions.values.toList()
    }
}