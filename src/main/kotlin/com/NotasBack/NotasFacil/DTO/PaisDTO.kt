package com.NotasBack.NotasFacil.DTO

import com.NotasBack.NotasFacil.model.Aluno
import java.util.*

data class PaisRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val filhos: List<UUID> = listOf()
)
    
    
