package com.NotasBack.NotasFacil.repository

import com.NotasBack.NotasFacil.model.Aluno
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface AlunoRepository : JpaRepository<Aluno, UUID> {
    fun findByEmail(email: String): Aluno?
}