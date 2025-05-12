package com.NotasBack.NotasFacil.DTO

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*


data class ProfessorRequest(
    @field:NotBlank val nome: String,
    @field:NotBlank @field:Email val email: String,
    @field:NotBlank val senha: String,
    val disciplinasIds: List<String> // Corrigido de UUID para String para facilitar serialização
)

