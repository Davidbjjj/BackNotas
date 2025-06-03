
package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.LoginRequest
import com.NotasBack.NotasFacil.DTO.LoginResponse
import com.NotasBack.NotasFacil.DTO.ProfessorResponseDTO
import com.NotasBack.NotasFacil.exception.UnauthorizedException
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import com.NotasBack.NotasFacil.security.JwtTokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val professorRepository: ProfessorRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService,
    private val professorService: ProfessorService
) {
    fun login(loginRequest: LoginRequest): LoginResponse {
        val professor = professorRepository.findByEmail(loginRequest.email)
            .orElseThrow { UnauthorizedException("Email ou senha inválidos") }

        if (!passwordEncoder.matches(loginRequest.senha, professor.senha)) {
            throw UnauthorizedException("Email ou senha inválidos")
        }

        val token = jwtTokenService.generateToken(professor.email)
        val professorDTO = professorService.toResponseDTO(professor)

        return LoginResponse(token, professorDTO)
    }
}