package com.NotasBack.NotasFacil.model

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.*

@Entity
data class Aluno(
    @Id
    val id: UUID = UUID.randomUUID(),

    val nome: String,

    @Column(unique = true)
    val email: String,

    val senha: String,

    @ElementCollection
    val notas: List<Double> = listOf(),

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    val professor: Professor? = null,

    @ManyToOne
    @JoinColumn(name = "pais_id")
    @JsonBackReference
    val pais: Pais? = null,

    @ManyToMany
    @JoinTable(
        name = "aluno_disciplina",
        joinColumns = [JoinColumn(name = "aluno_id")],
        inverseJoinColumns = [JoinColumn(name = "disciplina_id")]
    )
    @JsonBackReference
    val disciplinas: MutableList<Disciplina> = mutableListOf()
)
