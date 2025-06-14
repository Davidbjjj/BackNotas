package com.NotasBack.NotasFacil.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
data class Notificacao(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    val titulo: String,

    val mensagem: String,

    val usuarioId: UUID,

    val lida: Boolean = false,

    @Column(name = "criada_em") // <-- adiciona o nome da coluna esperada
    val criadaEm: LocalDateTime = LocalDateTime.now()
)

