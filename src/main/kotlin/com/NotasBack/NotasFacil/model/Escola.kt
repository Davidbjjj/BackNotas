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

    val endereco: String,

    val role: String = "ESCOLA",

    @ElementCollection
    @CollectionTable(name = "escola_emails_permitidos", joinColumns = [JoinColumn(name = "escola_id")])
    @Column(name = "email_permitido")
    val emailsPermitidos: MutableSet<String> = mutableSetOf()
)