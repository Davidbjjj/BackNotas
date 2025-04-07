package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.ProfessorRequest
import com.NotasBack.NotasFacil.model.Professor
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfessorService(
    private val repository: ProfessorRepository
) {
    fun criar(request: ProfessorRequest): Professor {
        val professor = Professor(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            disciplinas = request.disciplinas
        )
        return repository.save(professor)
    }

    fun listar(): List<Professor> = repository.findAll()

    fun buscarPorId(id: UUID): Professor = repository.findById(id).orElseThrow {
        NoSuchElementException("Professor não encontrado")
    }

    fun atualizar(id: UUID, request: ProfessorRequest): Professor {
        val existente = buscarPorId(id)
        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            disciplinas = request.disciplinas
        )
        return repository.save(atualizado)
    }

    fun deletar(id: UUID) {
        repository.deleteById(id)
    }
}

