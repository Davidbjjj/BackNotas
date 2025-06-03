package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.EscolaLoginRequest
import com.NotasBack.NotasFacil.DTO.EscolaLoginResponse
import com.NotasBack.NotasFacil.exception.UnauthorizedException
import com.NotasBack.NotasFacil.security.JwtTokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthEscolaService(
    private val escolaService: EscolaService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService
) {
    fun login(loginRequest: EscolaLoginRequest): EscolaLoginResponse {
        val escola = escolaService.buscarPorEmail(loginRequest.email)
            ?: throw UnauthorizedException("Email ou senha inválidos")

        if (!passwordEncoder.matches(loginRequest.senha, escola.senha)) {
            throw UnauthorizedException("Email ou senha inválidos")
        }

        val token = jwtTokenService.generateToken(escola.email)
        val escolaDTO = escolaService.toResponseDTO(escola)

        return EscolaLoginResponse(token, escolaDTO)
    }
}