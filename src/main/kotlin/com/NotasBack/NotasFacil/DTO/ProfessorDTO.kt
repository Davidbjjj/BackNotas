package com.NotasBack.NotasFacil.DTO



data class ProfessorRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val disciplinas: List<String> = listOf()
)
