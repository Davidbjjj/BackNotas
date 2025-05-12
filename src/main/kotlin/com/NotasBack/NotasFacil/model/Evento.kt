package com.NotasBack.NotasFacil

import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.model.Disciplina
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
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
    @JsonBackReference
    val disciplina: Disciplina,

    @ManyToMany
    @JoinTable(
        name = "evento_aluno",
        joinColumns = [JoinColumn(name = "evento_id")],
        inverseJoinColumns = [JoinColumn(name = "aluno_id")]
    )
    @JsonManagedReference
    val alunos: MutableList<Aluno> = mutableListOf()
)
