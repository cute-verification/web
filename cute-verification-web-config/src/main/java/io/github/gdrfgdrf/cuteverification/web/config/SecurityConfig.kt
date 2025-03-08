package io.github.gdrfgdrf.cuteverification.web.config

import io.github.gdrfgdrf.cuteverification.web.interfaces.IJwtAuthenticationTokenFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutFilter
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
open class SecurityConfig {
    @Autowired
    private lateinit var entryPoint: AuthenticationEntryPoint
    @Autowired
    private lateinit var accessDeniedHandler: AccessDeniedHandler
    @Autowired
    private lateinit var successHandler: AuthenticationSuccessHandler
    @Autowired
    private lateinit var failureHandler: AuthenticationFailureHandler
    @Autowired
    private lateinit var logoutSuccessHandler: LogoutSuccessHandler
    @Autowired
    private lateinit var authenticationTokenFilter: IJwtAuthenticationTokenFilter

    @Bean
    open fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

    @Bean
    open fun corsConfigurationSource(): CorsConfigurationSource {
        val config = CorsConfiguration()
        config.addAllowedOriginPattern("*")
        config.addAllowedMethod("*")
        config.addAllowedHeader("*")
        config.allowCredentials = true
        config.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    open fun configure(security: HttpSecurity): SecurityFilterChain {
        security.cors { cors ->
            cors.configurationSource(corsConfigurationSource())
        }.csrf { csrf ->
            csrf.disable()
        }.sessionManagement { session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/api/v1/admin/login").permitAll()
                .requestMatchers("/api/v1/admin/logout").permitAll()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers("/websocket").permitAll()
                .anyRequest()
                .authenticated()
        }.formLogin { formLogin ->
            formLogin.successHandler(successHandler)
                .failureHandler(failureHandler)
                .loginProcessingUrl("/api/v1/admin/login")
        }.exceptionHandling { exceptionHandling ->
            exceptionHandling.authenticationEntryPoint(entryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        }.addFilterBefore(
            authenticationTokenFilter,
            UsernamePasswordAuthenticationFilter::class.java
        ).addFilterBefore(
            authenticationTokenFilter,
            LogoutFilter::class.java,
        ).headers { headers ->
            headers.cacheControl { cacheControlConfig ->
                cacheControlConfig.disable()
            }
        }.logout { logout ->
            logout.logoutUrl("/api/v1/admin/logout")
                .logoutSuccessHandler(logoutSuccessHandler)

        }

        return security.build()
    }

}