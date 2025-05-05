package com.NotasBack.NotasFacil.model


import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
data class Evento(
    @Id
    val id: UUID = UUID.randomUUID(),

    var titulo: String,
    var descricao: String,
    var notaMaxima: Double,
    var data: LocalDateTime,

    @ElementCollection
    var arquivos: List<String> = listOf(),

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    val disciplina: Disciplina, // Evento pertence a uma disciplina

    @ManyToMany
    @JoinTable(
        name = "evento_aluno",
        joinColumns = [JoinColumn(name = "evento_id")],
        inverseJoinColumns = [JoinColumn(name = "aluno_id")]
    )
    val alunos: MutableList<Aluno> = mutableListOf() // Alunos associados (opcional, se quiser filtrar por aluno)
)