package io.github.gdrfgdrf.cuteverification.web.interfaces

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessageTypes

interface ISessionSender {
    fun send(id: String, type: WsMessageTypes, provider: (MutableMap<String, Any?>.() -> Unit)?)
    fun send(id: String, type: WsMessageTypes)

    fun broadcast(type: WsMessageTypes, provider: (MutableMap<String, Any?>.() -> Unit))
    fun broadcast(type: WsMessageTypes)

    fun restrictionCreated(restrictionId: String)
    fun restrictionRestored(restrictionId: String)
    fun restrictionStarted(restrictionIds: List<String>)
}