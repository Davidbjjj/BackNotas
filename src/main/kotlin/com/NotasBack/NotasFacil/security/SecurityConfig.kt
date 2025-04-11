package com.NotasBack.NotasFacil.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // desativa CSRF (ideal só para desenvolvimento/testes)
            .authorizeHttpRequests {
                it.anyRequest().permitAll() // libera TODAS as rotas
            }

        return http.build()
    }
}
