package com.NotasBack.NotasFacil.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenService: JwtTokenService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                auth
                    // Permite acesso público a esses endpoints específicos
                    .requestMatchers(
                        "/professores/login",
                        "/professores",
                        "/alunos",// criação de professor
                        "/swagger-ui/**", // se estiver usando Swagger
                        "/v3/api-docs/**" // documentação OpenAPI
                    ).permitAll()
                    // Exige autenticação para qualquer path que comece com /auth/
                    .requestMatchers("/auth/**").authenticated()
                    // Permite todos os outros requests (ou ajuste conforme necessidade)
                    .anyRequest().permitAll()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenService),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}