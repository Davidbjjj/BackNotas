package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.EscolaLoginRequest
import com.NotasBack.NotasFacil.DTO.EscolaLoginResponse
import com.NotasBack.NotasFacil.exception.UnauthorizedException
import com.NotasBack.NotasFacil.repository.EscolaRepository
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
    private val authenticationManager: AuthenticationManager,
    private val escolaRepository: EscolaRepository
) {
    fun login(loginRequest: EscolaLoginRequest): EscolaLoginResponse {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.email,
                loginRequest.senha
            )
        )

        val escola = escolaRepository.findByEmail(loginRequest.email)
            .orElseThrow { UnauthorizedException("Email ou senha inválidos") }

        if (!passwordEncoder.matches(loginRequest.senha, escola.senha)) {
            throw UnauthorizedException("Email ou senha inválidos")
        }

        val token = jwtTokenService.generateToken(escola.email, escola.role)
        val escolaDTO = escolaService.toResponseDTO(escola)

        return EscolaLoginResponse(token, escolaDTO)
    }
}