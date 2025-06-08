package com.NotasBack.NotasFacil.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
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

    val role: String = "PROFESSOR", // Adicionando a role

    @OneToMany(mappedBy = "professor", cascade = [CascadeType.ALL])
    @JsonIgnoreProperties("professor", "alunos", "eventos")
    val disciplinas: MutableList<Disciplina> = mutableListOf(),

    @OneToMany(mappedBy = "professor", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnoreProperties("professor", "disciplinas", "pais")
    val alunos: List<Aluno> = listOf(),

    @ManyToOne
    @JoinColumn(name = "escola_id")
    val escola: Escola? =null

)