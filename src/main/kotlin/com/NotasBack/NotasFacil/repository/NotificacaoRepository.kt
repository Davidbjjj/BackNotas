package com.NotasBack.NotasFacil.repository


import com.NotasBack.NotasFacil.model.Notificacao

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NotificacaoRepository : JpaRepository<Notificacao, UUID> {
    fun findByUsuarioIdOrderByCriadaEmDesc(usuarioId: UUID): List<Notificacao>

}
