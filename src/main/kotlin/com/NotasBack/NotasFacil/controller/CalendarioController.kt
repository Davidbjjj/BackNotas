package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.CalendarioEventoDTO
import com.NotasBack.NotasFacil.service.EventoService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/calendario")
class CalendarioController(private val eventoService: EventoService) {

    @GetMapping
    fun buscarEventosPorDataEDisciplina(
        @RequestParam data: LocalDate,
        @RequestParam disciplinaId: UUID
    ): List<CalendarioEventoDTO> {
        val eventos = eventoService.buscarEventosPorDataEDisciplina(data, disciplinaId)
        return eventos.map { evento ->
            CalendarioEventoDTO(
                titulo = evento.titulo,
                descricao = evento.descricao,
                data = evento.data,
                alunos = evento.alunos.map { aluno -> aluno.nome },
                disciplina = evento.disciplina.nome,

            )
        }
    }
}
