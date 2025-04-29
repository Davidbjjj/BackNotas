package com.NotasBack.NotasFacil.service


import com.NotasBack.NotasFacil.DTO.AlunoRequest
import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.repository.AlunoRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException

@Service
class AlunoService(
    private val repository: AlunoRepository,
    private val professorService: ProfessorService
) {
    fun criar(request: AlunoRequest): Aluno {
        val professor = request.professorId?.let { professorService.buscarPorId(it) }
        val aluno = Aluno(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            notas = request.notas,
            professor = professor
        )
        return repository.save(aluno)
    }

    fun listar(): List<Aluno> = repository.findAll()

    fun buscarPorId(id: UUID): Aluno = repository.findById(id).orElseThrow {
        NoSuchElementException("Aluno não encontrado")
    }

    fun atualizar(id: UUID, request: AlunoRequest): Aluno {
        val existente = buscarPorId(id)
        val professor = request.professorId?.let { professorService.buscarPorId(it) }
        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            notas = request.notas,
            professor = professor
        )
        return repository.save(atualizado)
    }

    fun deletar(id: UUID) {
        repository.deleteById(id)
    }
}
