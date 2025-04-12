package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Escola
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EscolaRepository : JpaRepository<Escola, UUID> {
    fun findByEmail(email: String): Escola?
}