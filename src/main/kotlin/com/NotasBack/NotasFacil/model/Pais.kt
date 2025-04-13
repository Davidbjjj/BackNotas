package com.NotasBack.NotasFacil.model

import jakarta.persistence.*
import java.util.*


@Entity
data class Pais(
    @Id
    val id: UUID = UUID.randomUUID(),

    val nome: String,

    @Column(unique = true)
    val email: String,

    val senha: String,

    @OneToMany(mappedBy = "pais", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val filhos: List<Aluno> = listOf()
)