package io.github.gdrfgdrf.cuteverification.web.timers

import io.github.gdrfgdrf.cuteverification.web.commons.pojo.websocket.WsMessageTypes
import io.github.gdrfgdrf.cuteverification.web.interfaces.IRestrictionService
import io.github.gdrfgdrf.cuteverification.web.interfaces.ISessionSender
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class CheckRestrictionTimer {
    @Autowired
    private lateinit var restrictionService: IRestrictionService
    @Autowired
    private lateinit var sessionSender: ISessionSender

    private var notStartedRestrictionId = arrayListOf<String>()

    @Scheduled(cron = "*/5 * * * * ?")
    fun check() {
        val now = Instant.now()
        val restrictions = restrictionService.list(false)
        val newStartedRestrictions = restrictions.stream()
            .filter { restriction ->
                val id = restriction.id!!
                val startAt = restriction.startAt
                if (now < startAt) {
                    if (!notStartedRestrictionId.contains(id)) {
                        notStartedRestrictionId.add(id)
                    }
                    return@filter false
                }

                if (notStartedRestrictionId.contains(id)) {
                    notStartedRestrictionId.remove(id)
                    return@filter true
                }

                return@filter false
            }.toList()
        if (newStartedRestrictions.isNullOrEmpty()) {
            return
        }

        val ids = newStartedRestrictions.stream()
            .map {
                it.id
            }
            .toList()

        sessionSender.broadcast(WsMessageTypes.RESTRICTION_STARTED) {
            put("restriction-ids", ids)
        }
    }

}