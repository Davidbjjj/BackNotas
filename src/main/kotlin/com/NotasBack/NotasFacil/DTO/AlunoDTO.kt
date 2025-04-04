package com.NotasBack.NotasFacil.DTO

import java.util.*

data class AlunoRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val notas: List<Double> = listOf(),
    val professorId: UUID? = null // opcional, pode ser nulo
)
