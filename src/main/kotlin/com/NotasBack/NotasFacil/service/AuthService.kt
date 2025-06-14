package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.*
import com.NotasBack.NotasFacil.exception.UnauthorizedException
import com.NotasBack.NotasFacil.model.*
import com.NotasBack.NotasFacil.repository.*
import com.NotasBack.NotasFacil.security.JwtTokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthService(
    private val professorRepository: ProfessorRepository,
    private val escolaRepository: EscolaRepository,
    private val alunoRepository: AlunoRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenService: JwtTokenService
) {
    fun login(request: LoginRequest): LoginResponse {
        val user = findUserByEmail(request.email)
            ?: throw UnauthorizedException("Credenciais inválidas")

        if (!passwordEncoder.matches(request.senha, user.senha)) {
            throw UnauthorizedException("Credenciais inválidas")
        }

        return createLoginResponse(user)
    }

    private fun findUserByEmail(email: String): AnyUser? {
        // Tentar encontrar como professor
        professorRepository.findByEmail(email)
            ?.takeIf { it.isPresent }
            ?.let { return ProfessorWrapper(it.get()) }

        // Tentar encontrar como escola
        escolaRepository.findByEmail(email)
            ?.takeIf { it.isPresent }
            ?.let { return EscolaWrapper(it.get()) }

        // Tentar encontrar como aluno
        alunoRepository.findByEmail(email)
            ?.let { return AlunoWrapper(it) }

        return null
    }

    private fun createLoginResponse(user: AnyUser): LoginResponse {
        return when (user) {
            is EscolaWrapper -> {
                val escolaDTO = EscolaResponseDTO(
                    nome = user.nome,
                    email = user.email,
                    endereco = user.endereco,
                    role = user.role,
                    emailsPermitidos = user.emailsPermitidos,
                    professores = user.professoresNomes
                )
                LoginResponse(
                    token = jwtTokenService.generateToken(user.email, user.role),
                    userDetails = escolaDTO
                )
            }
            is ProfessorWrapper -> {
                val professorDTO = ProfessorResponseDTO(
                    id = user.id,
                    nome = user.nome,
                    email = user.email,
                    role = user.role,
                    escolaNome = user.escolaNome,
                    disciplinas = user.disciplinasNomes
                )
                LoginResponse(
                    token = jwtTokenService.generateToken(user.email, user.role),
                    userDetails = professorDTO
                )
            }
            is AlunoWrapper -> {
                val alunoDTO = AlunoResponseDTO(
                    id = user.id,
                    nome = user.nome,
                    email = user.email,
                    role = user.role,
                    notas = user.notas,
                    disciplinas = user.disciplinasNomes
                )
                LoginResponse(
                    token = jwtTokenService.generateToken(user.email, user.role),
                    userDetails = alunoDTO
                )
            }
        }
    }

    // Interface e classes wrapper com todas as propriedades necessárias
    private sealed interface AnyUser {
        val id: UUID
        val nome: String
        val email: String
        val senha: String
        val role: String
    }

    private inner class EscolaWrapper(private val escola: Escola) : AnyUser {
        override val id: UUID get() = escola.id
        override val nome: String get() = escola.nome
        override val email: String get() = escola.email
        override val senha: String get() = escola.senha
        override val role: String get() = escola.role
        val endereco: String get() = escola.endereco
        val emailsPermitidos: Set<String> get() = escola.emailsPermitidos
        val professoresNomes: List<String> get() = escolaRepository
            .findProfessoresByEscolaId(escola.id).map { it.nome }
    }

    private inner class ProfessorWrapper(private val professor: Professor) : AnyUser {
        override val id: UUID get() = professor.id
        override val nome: String get() = professor.nome
        override val email: String get() = professor.email
        override val senha: String get() = professor.senha
        override val role: String get() = professor.role ?: "PROFESSOR"
        val escolaNome: String? get() = professor.escola?.nome
        val disciplinasNomes: List<String> get() = professor.disciplinas.map { it.nome }
    }

    private inner class AlunoWrapper(private val aluno: Aluno) : AnyUser {
        override val id: UUID get() = aluno.id
        override val nome: String get() = aluno.nome
        override val email: String get() = aluno.email
        override val senha: String get() = aluno.senha
        override val role: String get() = aluno.role
        val notas: List<Double> get() = aluno.notas
        val disciplinasNomes: List<String> get() = aluno.disciplinas.map { it.nome }
    }
}