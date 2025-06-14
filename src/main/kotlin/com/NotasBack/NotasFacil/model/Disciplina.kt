package com.NotasBack.NotasFacil.model

import com.NotasBack.NotasFacil.Evento
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.util.*

@Entity
data class Disciplina(
    @Id
    val id: UUID = UUID.randomUUID(),

    var nome: String,

    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    var professor: Professor,

    @ManyToMany(mappedBy = "disciplinas")
    val alunos: MutableList<Aluno> = mutableListOf(), // Alunos matriculados

    @OneToMany(mappedBy = "disciplina", cascade = [CascadeType.ALL])
    val eventos: MutableList<Evento> = mutableListOf(), // Eventos (provas/atividades) da disciplina

    @ManyToOne
    @JoinColumn(name = "escola_id")
    val escola: Escola? =null

)