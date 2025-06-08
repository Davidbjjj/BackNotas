package com.NotasBack.NotasFacil.DTO



data class EscolaRequest(
    val nome: String,
    val email: String,
    val senha: String,
    val endereco: String
)
// Adicione no arquivo DTO/EscolaRequest.kt
data class EscolaLoginRequest(
    val email: String,
    val senha: String
)

data class EscolaLoginResponse(
    val token: String,
    val escola: EscolaResponseDTO
)

data class EscolaResponseDTO(
    val nome: String,
    val email: String,
    val endereco: String,
    val role: String,
    val emailsPermitidos: Set<String>,
    val professores: List<String> = emptyList()
)