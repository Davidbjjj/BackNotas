package com.NotasBack.NotasFacil.security

import TokenService
import com.auth0.jwt.JWT
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter


class JwtAuthenticationFilter(
    private val jwtTokenService: TokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = recuperarToken(request)
        if (token != null) {
            try {
                val subject = jwtTokenService.validarToken(token)
                if (subject != null && !jwtTokenService.isTokenRevoked(token)) {
                    val decodedJWT = JWT.decode(token)
                    val role = decodedJWT.getClaim("role").asString()

                    val authorities = mutableListOf<SimpleGrantedAuthority>()
                    authorities.add(SimpleGrantedAuthority("ROLE_$role"))

                    val authentication = UsernamePasswordAuthenticationToken(
                        subject,
                        null,
                        authorities
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                }
            } catch (e: Exception) {
                logger.error("Não foi possível autenticar o usuário: ${e.message}")
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader("Authorization")
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7)
        }
        return null
    }
}