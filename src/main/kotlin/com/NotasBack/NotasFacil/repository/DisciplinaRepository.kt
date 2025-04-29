package com.NotasBack.NotasFacil.repository


import com.NotasBack.NotasFacil.model.Disciplina
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DisciplinaRepository : JpaRepository<Disciplina, UUID> {
    fun findByProfessorId(professorId: UUID): List<Disciplina>
}