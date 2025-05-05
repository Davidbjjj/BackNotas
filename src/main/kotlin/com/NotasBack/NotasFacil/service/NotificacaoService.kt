package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.model.Notificacao
import com.NotasBack.NotasFacil.repository.NotificacaoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificacaoService(
    private val notificacaoRepository: NotificacaoRepository
) {

    fun criarNotificacao(titulo: String, mensagem: String, usuarioId: UUID): Notificacao {
        val nova = Notificacao(
            titulo = titulo,
            mensagem = mensagem,
            usuarioId = usuarioId
        )
        return notificacaoRepository.save(nova)
    }

    fun listarNotificacoes(usuarioId: UUID): List<Notificacao> {
        return notificacaoRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuarioId)
    }

    fun marcarComoLida(id: UUID): Notificacao? {
        val notificacao = notificacaoRepository.findById(id).orElse(null)
        if (notificacao != null) {
            val atualizada = notificacao.copy(lida = true)
            return notificacaoRepository.save(atualizada)
        }
        return null
    }
}