package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.AlunoRequest
import com.NotasBack.NotasFacil.DTO.AlunoResponseDTO
import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.repository.AlunoRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class AlunoService(
    private val repository: AlunoRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun criar(request: AlunoRequest): Aluno {
        val aluno = Aluno(
            nome = request.nome,
            email = request.email,
            senha = passwordEncoder.encode(request.senha),
            notas = request.notas
        )
        return repository.save(aluno)
    }

    fun listar(): List<Aluno> = repository.findAll()

    fun buscarPorId(id: UUID): Aluno = repository.findById(id).orElseThrow {
        NoSuchElementException("Aluno não encontrado")
    }

    fun buscarPorEmail(email: String): Aluno? = repository.findByEmail(email)

    fun atualizar(id: UUID, request: AlunoRequest): Aluno {
        val existente = buscarPorId(id)
        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = passwordEncoder.encode(request.senha),
            notas = request.notas
        )
        return repository.save(atualizado)
    }

    fun deletar(id: UUID) {
        repository.deleteById(id)
    }

    fun toResponseDTO(aluno: Aluno): AlunoResponseDTO {
        return AlunoResponseDTO(
            id = aluno.id,
            nome = aluno.nome,
            email = aluno.email,
            role = aluno.role,
            notas = aluno.notas,
            disciplinas = aluno.disciplinas.map { it.nome }
        )
    }
}