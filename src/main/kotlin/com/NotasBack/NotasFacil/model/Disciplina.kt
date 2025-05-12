package com.NotasBack.NotasFacil.model

import com.NotasBack.NotasFacil.Evento
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

import jakarta.persistence.*
import java.util.*

@Entity
data class Disciplina(
    @Id
    val id: UUID = UUID.randomUUID(),

    var nome: String,

    @ManyToOne
    @JoinColumn(name = "professor_id")

    @JsonBackReference // Evita a serialização recursiva
    var professor: Professor,

    @ManyToMany(mappedBy = "disciplinas")
    @JsonManagedReference
    val alunos: MutableList<Aluno> = mutableListOf(),

    @OneToMany(mappedBy = "disciplina", cascade = [CascadeType.ALL])
    @JsonManagedReference
    val eventos: MutableList<Evento> = mutableListOf()
)
