package io.github.gdrfgdrf.cuteverification.web.commons.pojo

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class RestrictionCreationDTO(
    @JsonProperty("start-at")
    val startAt: Long,
    @JsonProperty("expire-at")
    val expireAt: Long,
    val targets: List<String>
) {
    fun buildStartAt(): Instant {
        return Instant.ofEpochMilli(startAt)
    }

    fun buildExpireAt(): Instant {
        return Instant.ofEpochMilli(expireAt)
    }
}