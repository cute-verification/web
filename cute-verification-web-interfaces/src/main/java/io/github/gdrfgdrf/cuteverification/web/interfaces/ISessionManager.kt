package io.github.gdrfgdrf.cuteverification.web.interfaces

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.Session

interface ISessionManager {
    fun get(id: String): Session?
    fun add(id: String, session: Session)
    fun remove(id: String)

    fun auth(session: Session): Boolean
    fun kick(id: String)

    fun sessions(): List<Session>
}