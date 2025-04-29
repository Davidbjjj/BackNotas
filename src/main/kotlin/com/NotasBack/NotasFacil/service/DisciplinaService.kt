package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.DisciplinaDTO
import com.NotasBack.NotasFacil.model.Disciplina
import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.repository.AlunoRepository
import com.NotasBack.NotasFacil.repository.DisciplinaRepository
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DisciplinaService(
    private val disciplinaRepository: DisciplinaRepository,
    private val professorRepository: ProfessorRepository,
    private val alunoRepository: AlunoRepository
) {

    // --- CRUD Básico ---
    @Transactional
    fun criarDisciplina(dto: DisciplinaDTO): Disciplina {
        val professor = professorRepository.findById(dto.professorId)
            .orElseThrow { NoSuchElementException("Professor não encontrado") }

        val disciplina = Disciplina(
            nome = dto.nome,
            professor = professor
        )

        return disciplinaRepository.save(disciplina).also {
            professor.disciplinas.add(it)
        }
    }

    fun listarTodas(): List<Disciplina> {
        return disciplinaRepository.findAll()
    }

    fun buscarPorId(id: UUID): Disciplina {
        return disciplinaRepository.findById(id)
            .orElseThrow { NoSuchElementException("Disciplina não encontrada") }
    }

    @Transactional
    fun atualizarDisciplina(id: UUID, dto: DisciplinaDTO): Disciplina {
        val disciplina = buscarPorId(id)
        disciplina.nome = dto.nome
        return disciplinaRepository.save(disciplina)
    }

    @Transactional
    fun deletarDisciplina(id: UUID) {
        disciplinaRepository.deleteById(id)
    }

    // --- Associações ---
    @Transactional
    fun associarProfessor(disciplinaId: UUID, professorId: UUID): Disciplina {
        val disciplina = buscarPorId(disciplinaId)
        val professor = professorRepository.findById(professorId)
            .orElseThrow { NoSuchElementException("Professor não encontrado") }

        disciplina.professor = professor
        professor.disciplinas.add(disciplina)

        return disciplinaRepository.save(disciplina)
    }

    @Transactional
    fun matricularAluno(disciplinaId: UUID, alunoId: UUID): Disciplina {
        val disciplina = buscarPorId(disciplinaId)
        val aluno = alunoRepository.findById(alunoId)
            .orElseThrow { NoSuchElementException("Aluno não encontrado com ID: $alunoId") }

        if (disciplina.alunos.any { it.id == alunoId }) {
            throw IllegalStateException("Aluno já matriculado nesta disciplina")
        }

        disciplina.alunos.add(aluno)
        aluno.disciplinas.add(disciplina)

        return disciplinaRepository.save(disciplina)
    }

    @Transactional
    fun desmatricularAluno(disciplinaId: UUID, alunoId: UUID) {
        val disciplina = buscarPorId(disciplinaId)
        val aluno = alunoRepository.findById(alunoId)
            .orElseThrow { NoSuchElementException("Aluno não encontrado com ID: $alunoId") }

        if (!disciplina.alunos.contains(aluno)) {
            throw IllegalStateException("Aluno não está matriculado nesta disciplina")
        }

        disciplina.alunos.remove(aluno)
        aluno.disciplinas.remove(disciplina)

        disciplinaRepository.save(disciplina)
    }

    fun listarAlunos(disciplinaId: UUID): List<Aluno> {
        val disciplina = buscarPorId(disciplinaId)
        return disciplina.alunos.toList()
    }
}
