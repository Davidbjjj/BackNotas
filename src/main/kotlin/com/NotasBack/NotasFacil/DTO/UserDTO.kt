package com.NotasBack.NotasFacil.DTO

import java.util.*


data class UserResponse(
    val email: String,
    val tipo: String,
    val id: UUID,
    val nome: String,
    val detalhes: Map<String, Any?> = emptyMap()
)