package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Escola
import com.NotasBack.NotasFacil.model.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EscolaRepository : JpaRepository<Escola, UUID> {
    fun findByEmail(email: String): Optional<Escola>
    fun findByNome(nome:String):Optional<Escola>
    @Query("SELECT p FROM Professor p WHERE p.escola.id = :escolaId")
    fun findProfessoresByEscolaId(escolaId: UUID): List<Professor>

}
