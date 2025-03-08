package io.github.gdrfgdrf.cuteverification.web.config

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptPasswordEncoderBean : BCryptPasswordEncoder() {
}