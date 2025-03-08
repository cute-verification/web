package io.github.gdrfgdrf.cuteverification.web

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@MapperScan("io.github.gdrfgdrf.cuteverification.web.mappers")
@SpringBootApplication
@EnableScheduling
open class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<Application>(*args)
        }
    }
}