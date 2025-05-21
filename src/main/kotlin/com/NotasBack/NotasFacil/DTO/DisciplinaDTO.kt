package com.NotasBack.NotasFacil.DTO

import jakarta.validation.constraints.Email
import java.util.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class DisciplinaDTO(
    @field:NotBlank val nome: String,
    @field:NotBlank val escola:String,
    @field:NotBlank @field:Email val professorEmail: String
)

data class DisciplinaResponseDTO(
    val id: UUID,
    val nome: String,
    val professorNome: String,
    val professorEmail: String,
    val escola: String
)

data class AlunoResponseDTO(
    val id: UUID,
    val nome: String
)
