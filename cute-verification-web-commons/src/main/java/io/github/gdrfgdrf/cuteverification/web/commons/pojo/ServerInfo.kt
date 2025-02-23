package io.github.gdrfgdrf.cuteverification.web.commons.pojo

import org.slf4j.LoggerFactory
import java.lang.management.ManagementFactory
import java.time.Instant

data class ServerInfo(
    var name: String?,
    var version: String?,
    val time: Time,
    val jvm: Jvm,
    val os: Os,
) {
    data class Time(
        val start: Instant,
        val current: Instant
    ) {
        companion object {
            private val logger = LoggerFactory.getLogger(Time::class.java)

            val start: Instant = Instant.now()
            init {
                logger.info("Start time: $start")
            }

            fun collect(): Time {
                return Time(
                    start = start,
                    current = Instant.now()
                )
            }
        }
    }

    data class Jvm(
        val version: String,
        val uptime: Long,
        val vendor: String,
    ) {
        companion object {
            fun collect(): Jvm {
                val runtimeBean = ManagementFactory.getRuntimeMXBean()
                return Jvm(
                    version = System.getProperty("java.version"),
                    uptime = runtimeBean.uptime,
                    vendor = System.getProperty("java.vm.vendor"),
                )
            }
        }
    }

    data class Os(
        val name: String,
        val version: String,
        val architecture: String
    ) {
        companion object {
            fun collect(): Os {
                return Os(
                    name = System.getProperty("os.name"),
                    version = System.getProperty("os.version"),
                    architecture = System.getProperty("os.arch")
                )
            }
        }
    }

    companion object {
        fun collect(): ServerInfo {
            return ServerInfo(
                null,
                null,
                time = Time.collect(),
                jvm = Jvm.collect(),
                os = Os.collect(),
            )
        }
    }
}