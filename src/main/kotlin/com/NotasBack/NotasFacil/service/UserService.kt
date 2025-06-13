package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.UserResponse
import com.NotasBack.NotasFacil.repository.AlunoRepository
import com.NotasBack.NotasFacil.repository.EscolaRepository
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val alunoRepository: AlunoRepository,
    private val professorRepository: ProfessorRepository,
    private val escolaRepository: EscolaRepository
) {
    fun findUserByEmail(email: String): UserResponse {
        // Verifica se é um aluno
        alunoRepository.findByEmail(email)?.let { aluno ->
            return UserResponse(
                email = aluno.email,
                tipo = "ALUNO",
                id = aluno.id,
                nome = aluno.nome,
                detalhes = mapOf(
                    "notas" to aluno.notas,
                    "disciplinas" to aluno.disciplinas.map { it.nome }
                )
            )
        }

        // Verifica se é um professor
        professorRepository.findByEmail(email).orElse(null)?.let { professor ->
            return UserResponse(
                email = professor.email,
                tipo = "PROFESSOR",
                id = professor.id,
                nome = professor.nome,
                detalhes = mapOf(
                    "escola" to professor.escola?.nome,
                    "disciplinas" to professor.disciplinas.map { it.nome }
                )
            )
        }

        // Verifica se é uma escola
        escolaRepository.findByEmail(email)?.let { escola ->
            return UserResponse(
                email = escola.get().email,
                tipo = "ESCOLA",
                id = escola.get().id,
                nome = escola.get().nome,
                detalhes = mapOf(
                    "endereco" to escola.get().endereco,
                    "emailsPermitidos" to escola.get().emailsPermitidos
                )
            )
        }

        throw NoSuchElementException("Nenhum usuário encontrado com o email: $email")
    }
}