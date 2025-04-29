package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.EventoDTO
import com.NotasBack.NotasFacil.DTO.EventoResponseDTO
import com.NotasBack.NotasFacil.Evento
import com.NotasBack.NotasFacil.service.EventoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/eventos")
class EventoController(private val service: EventoService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarEvento(@RequestBody dto: EventoDTO): EventoResponseDTO {
        return toResponseDTO(service.criarEvento(dto))
    }

    @GetMapping("/disciplina/{disciplinaId}")
    fun listarPorDisciplina(@PathVariable disciplinaId: UUID): List<EventoResponseDTO> {
        return service.listarPorDisciplina(disciplinaId).map { toResponseDTO(it) }
    }

    @PostMapping("/{eventoId}/alunos/{alunoId}")
    fun adicionarAluno(
        @PathVariable eventoId: UUID,
        @PathVariable alunoId: UUID
    ): EventoResponseDTO {
        return toResponseDTO(service.adicionarAluno(eventoId, alunoId))
    }

    // --- Utils ---
    private fun toResponseDTO(evento: Evento): EventoResponseDTO {
        return EventoResponseDTO(
            id = evento.id,
            titulo = evento.titulo,
            descricao = evento.descricao,
            notaMaxima = evento.notaMaxima,
            data = evento.data,
            arquivos = evento.arquivos,
            disciplinaNome = evento.disciplina.nome,
            totalAlunos = evento.alunos.size
        )
    }
}