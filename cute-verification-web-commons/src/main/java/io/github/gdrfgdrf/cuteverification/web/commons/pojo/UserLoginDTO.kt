package io.github.gdrfgdrf.cuteverification.web.commons.pojo

data class UserLoginDTO(
    val username: String,
    val code: String,
    val platform: IdentificationPlatforms,
    val ip: String
) {


}