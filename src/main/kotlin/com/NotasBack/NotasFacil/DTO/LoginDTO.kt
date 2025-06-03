package com.NotasBack.NotasFacil.DTO

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank


// Adicione no arquivo DTO/ProfessorRequest.kt
data class LoginRequest(
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val senha: String
)

data class LoginResponse(
    val token: String,
    val professor: ProfessorResponseDTO
)