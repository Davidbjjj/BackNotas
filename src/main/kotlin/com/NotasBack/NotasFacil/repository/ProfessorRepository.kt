package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


interface ProfessorRepository : JpaRepository<Professor, UUID> {
    fun findByEmail(email: String): Optional<Professor>

}
