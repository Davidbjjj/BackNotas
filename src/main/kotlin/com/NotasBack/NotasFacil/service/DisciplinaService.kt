package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.DisciplinaDTO
import com.NotasBack.NotasFacil.DTO.DisciplinaResponseDTO
import com.NotasBack.NotasFacil.model.Disciplina
import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.model.Professor
import com.NotasBack.NotasFacil.repository.AlunoRepository
import com.NotasBack.NotasFacil.repository.DisciplinaRepository
import com.NotasBack.NotasFacil.repository.EscolaRepository
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DisciplinaService(
    private val disciplinaRepository: DisciplinaRepository,
    private val professorRepository: ProfessorRepository,
    private val alunoRepository: AlunoRepository,
    private val escolaRepository: EscolaRepository
) {

    // --- CRUD Básico ---
    @Transactional
    fun criarDisciplina(dto: DisciplinaDTO): DisciplinaResponseDTO {
        val professor = professorRepository.findByEmail(dto.professorEmail)
            .orElseThrow { NoSuchElementException("Professor com e-mail '${dto.professorEmail}' não encontrado") }

        val escola = escolaRepository.findByNome(dto.escola)
            .orElseThrow { NoSuchElementException("Escola '${dto.escola}' não encontrada") }

        if (professor.escola?.id != escola.id) {
            throw IllegalArgumentException("O professor '${professor.nome}' não pertence à escola '${escola.nome}'")
        }

        val disciplina = Disciplina(
            nome = dto.nome,
            professor = professor,
            escola = escola
        )

        val saved = disciplinaRepository.save(disciplina)
        professor.disciplinas.add(saved) // opcional se for necessário manter bidirecional

        return DisciplinaResponseDTO(
            id = saved.id,
            nome = saved.nome,
            professorNome = saved.professor.nome,
            professorEmail = saved.professor.email,
            escola = saved.escola?.nome ?: "Escola não informada"

        )
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
    fun associarProfessor(disciplinaNome: String, professorEmail: String): Disciplina {
        val disciplina = disciplinaRepository.findByNome(disciplinaNome)
            ?: throw NoSuchElementException("Disciplina com nome '$disciplinaNome' não encontrada.")

        val professor = professorRepository.findByEmail(professorEmail)
            .orElseThrow { NoSuchElementException("Professor com e-mail '$professorEmail' não encontrado.") }

        val disciplinaEscolaId = disciplina.escola?.id
        val professorEscolaId = professor.escola?.id

        if (disciplinaEscolaId != professorEscolaId) {
            throw IllegalArgumentException("Professor não pertence à mesma escola da disciplina.")
        }

        disciplina.professor = professor
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
    fun listarPorNomeEscola(nome: String): List<Disciplina> {
        return disciplinaRepository.findByEscolaNome(nome)
    }

}
