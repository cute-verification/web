package io.github.gdrfgdrf.cuteverification.web.config

import com.github.dreamyoung.mprelation.AutoMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class MPRelationConfig {

    @Bean
    open fun autoMapper(): AutoMapper {
        return AutoMapper(arrayOf(
            "io.github.gdrfgdrf.cuteverification.web.pojo.identification",
            "io.github.gdrfgdrf.cuteverification.web.pojo.record",
            "io.github.gdrfgdrf.cuteverification.web.pojo.restriction",
            "io.github.gdrfgdrf.cuteverification.web.pojo.role.admin",
            "io.github.gdrfgdrf.cuteverification.web.pojo.role.user"
        ))
    }

}