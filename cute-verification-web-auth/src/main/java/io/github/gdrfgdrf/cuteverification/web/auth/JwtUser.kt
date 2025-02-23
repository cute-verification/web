package io.github.gdrfgdrf.cuteverification.web.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class JwtUser(
    var username_: String? = null,
    var password_: String? = null,
    var authorities_: List<GrantedAuthority>? = null
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? = authorities_
    override fun getPassword(): String? = password_
    override fun getUsername(): String? = username_
}