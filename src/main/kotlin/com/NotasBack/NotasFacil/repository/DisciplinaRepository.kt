package com.NotasBack.NotasFacil.repository


import com.NotasBack.NotasFacil.model.Disciplina
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface DisciplinaRepository : JpaRepository<Disciplina, UUID> {
    fun findByProfessorId(professorId: UUID): List<Disciplina>
    @Query("SELECT d FROM Disciplina d WHERE d.escola.nome = :nomeEscola")
    fun findByEscolaNome(@Param("nomeEscola") nomeEscola: String): List<Disciplina>
    fun findByEscola_Nome(nome: String): List<Disciplina>
    fun findByNome(nome: String): Disciplina?


}