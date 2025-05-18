package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Escola
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EscolaRepository : JpaRepository<Escola, UUID> {
    fun findByEmail(email: String): Escola?
    fun findByNome(nome:String):Optional<Escola>
}
