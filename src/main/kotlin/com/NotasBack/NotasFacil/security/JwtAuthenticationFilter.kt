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
    private val jwtTokenService: JwtTokenService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getTokenFromRequest(request)

        if (!token.isNullOrEmpty() && jwtTokenService.validateToken(token)) {
            try {
                val email = jwtTokenService.getEmailFromToken(token)
                val role = jwtTokenService.getRoleFromToken(token)

                val authorities = listOf(SimpleGrantedAuthority(role))

                val authentication = UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    authorities
                )

                SecurityContextHolder.getContext().authentication = authentication
            } catch (e: Exception) {
                logger.error("Cannot set user authentication: ${e.message}")
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun getTokenFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}