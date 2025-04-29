package com.NotasBack.NotasFacil.model

import jakarta.persistence.*

import java.util.*


@Entity
data class Escola (

    @Id
    val id: UUID = UUID.randomUUID(),

    val nome: String,

    @Column(unique = true)
    val email: String,

    val senha: String,

    val endereco: String




)