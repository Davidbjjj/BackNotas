package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.AlunoLoginRequest
import com.NotasBack.NotasFacil.DTO.AlunoLoginResponse
import com.NotasBack.NotasFacil.exception.UnauthorizedException
import com.NotasBack.NotasFacil.security.JwtTokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthAlunoService(
    private val alunoService: AlunoService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService
) {
    fun login(loginRequest: AlunoLoginRequest): AlunoLoginResponse {
        val aluno = alunoService.buscarPorEmail(loginRequest.email)
            ?: throw UnauthorizedException("Email ou senha inválidos")

        if (!passwordEncoder.matches(loginRequest.senha, aluno.senha)) {
            throw UnauthorizedException("Email ou senha inválidos")
        }

        val token = jwtTokenService.generateToken(aluno.email, aluno.role)
        val alunoDTO = alunoService.toResponseDTO(aluno)

        return AlunoLoginResponse(token, alunoDTO)
    }
}