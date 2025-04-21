package com.NotasBack.NotasFacil.DTO

import java.util.*


data class ProfessorRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val disciplinasIds: List<UUID> // IDs das disciplinas
)

