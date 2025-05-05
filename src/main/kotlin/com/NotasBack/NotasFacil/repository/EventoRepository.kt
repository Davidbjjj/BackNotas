package com.NotasBack.NotasFacil.repository


import com.NotasBack.NotasFacil.Evento

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import java.util.*

interface EventoRepository : JpaRepository<Evento, UUID> {
    fun findByDisciplinaId(disciplinaId: UUID): List<Evento>

    fun findByDataBetweenAndDisciplina_Id(
        inicio: LocalDateTime,
        fim: LocalDateTime,
        disciplinaId: UUID
    ): List<Evento>

}
