package com.NotasBack.NotasFacil.repository


import com.NotasBack.NotasFacil.model.Evento

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventoRepository : JpaRepository<Evento, UUID> {
    fun findByDisciplinaId(disciplinaId: UUID): List<Evento>
}