package com.NotasBack.NotasFacil.DTO


import java.time.LocalDateTime
import java.util.*

data class EventoDTO(
    val titulo: String,
    val descricao: String,
    val notaMaxima: Double,
    val data: LocalDateTime,
    val arquivos: List<String>,
    val disciplinaId: UUID // ID da disciplina vinculada
)


data class EventoResponseDTO(
    val id: UUID,
    val titulo: String,
    val descricao: String,
    val notaMaxima: Double,
    val data: LocalDateTime,
    val arquivos: List<String>,
    val disciplinaNome: String, // Nome para exibição
    val totalAlunos: Int // Quantidade de alunos vinculados
)