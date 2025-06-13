package com.NotasBack.NotasFacil.DTO

import java.util.*

data class AlunoRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val notas: List<Double> = listOf(),
)
// Adicione no arquivo DTO/AlunoRequest.kt
data class AlunoLoginRequest(
    val email: String,
    val senha: String
)

data class AlunoLoginResponse(
    val token: String,
    val aluno: AlunoResponseDTO
)
data class AlunoResponseDTO(
    val id: UUID,
    val nome: String,
    val email: String,
    val role: String,
    val notas: List<Double>,
    val disciplinas: List<String>
)

