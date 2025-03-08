package io.github.gdrfgdrf.cuteverification.web.interfaces

interface IAuthenticator {
    fun auth(token: String): Boolean
    fun id(username: String): String?
    fun username(token: String): String?
}