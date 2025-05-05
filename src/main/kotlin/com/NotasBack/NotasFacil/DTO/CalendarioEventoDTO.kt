package com.NotasBack.NotasFacil.DTO

import java.time.LocalDateTime

class CalendarioEventoDTO (
    val titulo: String,
    val descricao: String,
    val data: LocalDateTime,
    val alunos: List<String>,
    val disciplina: String

)


