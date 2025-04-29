package com.NotasBack.NotasFacil.service


import com.NotasBack.NotasFacil.DTO.EventoDTO
import com.NotasBack.NotasFacil.Evento
import com.NotasBack.NotasFacil.model.*
import com.NotasBack.NotasFacil.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class EventoService(
    private val eventoRepository: EventoRepository,
    private val disciplinaRepository: DisciplinaRepository,
    private val alunoRepository: AlunoRepository
) {

    @Transactional
    fun criarEvento(dto: EventoDTO): Evento {
        val disciplina = disciplinaRepository.findById(dto.disciplinaId)
            .orElseThrow { NoSuchElementException("Disciplina não encontrada") }

        val evento = Evento(
            titulo = dto.titulo,
            descricao = dto.descricao,
            notaMaxima = dto.notaMaxima,
            data = dto.data,
            arquivos = dto.arquivos,
            disciplina = disciplina
        )

        evento.alunos.addAll(disciplina.alunos)

        return eventoRepository.save(evento)
    }

    fun listarPorDisciplina(disciplinaId: UUID): List<Evento> {
        return eventoRepository.findByDisciplinaId(disciplinaId)
    }

    @Transactional
    fun adicionarAluno(eventoId: UUID, alunoId: UUID): Evento {
        val evento = eventoRepository.findById(eventoId)
            .orElseThrow { NoSuchElementException("Evento não encontrado") }
        val aluno = alunoRepository.findById(alunoId)
            .orElseThrow { NoSuchElementException("Aluno não encontrado") }

        evento.alunos.add(aluno)
        return eventoRepository.save(evento)
    }
}