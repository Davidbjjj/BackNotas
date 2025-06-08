package com.NotasBack.NotasFacil.service

import TokenService
import com.NotasBack.NotasFacil.DTO.EscolaLoginRequest
import com.NotasBack.NotasFacil.DTO.EscolaLoginResponse
import com.NotasBack.NotasFacil.security.JwtTokenService

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthEscolaService(
    private val escolaService: EscolaService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService,
    private val authenticationManager: AuthenticationManager
) {
    fun login(request: EscolaLoginRequest): EscolaLoginResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.senha
            )
        )

        val escola = escolaService.buscarPorEmail(request.email)
            ?: throw NoSuchElementException("Escola não encontrada")

        if (!passwordEncoder.matches(request.senha, escola.senha)) {
            throw IllegalArgumentException("Senha inválida")
        }

        val token = jwtTokenService.generateToken(escola.email, escola.role)
        return EscolaLoginResponse(
            token = token,
            escola = escolaService.toResponseDTO(escola)
        )
    }
}