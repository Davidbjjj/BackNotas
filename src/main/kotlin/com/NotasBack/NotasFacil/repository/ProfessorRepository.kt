package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Professor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProfessorRepository : JpaRepository<Professor, UUID> {
    fun findByEmail(email: String): Professor?
}
