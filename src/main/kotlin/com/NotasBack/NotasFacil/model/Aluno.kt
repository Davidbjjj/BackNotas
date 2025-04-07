package com.NotasBack.NotasFacil.model


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
    val professor: Professor? = null
)
