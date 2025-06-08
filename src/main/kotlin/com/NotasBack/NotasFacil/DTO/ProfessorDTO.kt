package com.NotasBack.NotasFacil.DTO

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*

data class ProfessorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val senha: String,
    @field:NotBlank val escolaNome: String
)

data class ProfessorResponseDTO(
    val id: UUID,
    val nome: String,
    val email: String,
    val role: String,
    val escolaNome: String?,
    val disciplinas: List<String> = emptyList()
)