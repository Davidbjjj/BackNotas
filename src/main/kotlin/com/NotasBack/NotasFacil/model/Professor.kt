package com.NotasBack.NotasFacil.model;

import jakarta.persistence.*
import java.util.*

@Entity
data class Professor(
    @Id
    val id: UUID = UUID.randomUUID(),

    val nome: String,

    @Column(unique = true)
    val email: String,

    val senha: String,

    @OneToMany(mappedBy = "professor", cascade = [CascadeType.ALL])
    val disciplinas: MutableList<Disciplina> = mutableListOf(),

    @OneToMany(mappedBy = "professor", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val alunos: List<Aluno> = listOf()
)