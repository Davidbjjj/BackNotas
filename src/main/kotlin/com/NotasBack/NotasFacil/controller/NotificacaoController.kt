
    package com.NotasBack.NotasFacil.controller

    import com.NotasBack.NotasFacil.model.Notificacao
    import com.NotasBack.NotasFacil.service.NotificacaoService  
    import org.springframework.http.ResponseEntity
    import org.springframework.web.bind.annotation.*
    import java.util.*
    
    @RestController
    @RequestMapping("/notificacoes")
    class NotificacaoController(
        private val notificacaoService: NotificacaoService
    ) {
    
        @PostMapping
        fun criarNotificacao(
            @RequestParam titulo: String,
            @RequestParam mensagem: String,
            @RequestParam usuarioId: UUID
        ): ResponseEntity<Notificacao> {
            val nova = notificacaoService.criarNotificacao(titulo, mensagem, usuarioId)
            return ResponseEntity.ok(nova)
        }
    
        @GetMapping("/{usuarioId}")
        fun listar(@PathVariable usuarioId: UUID): ResponseEntity<List<Notificacao>> {
            return ResponseEntity.ok(notificacaoService.listarNotificacoes(usuarioId))
        }
    
        @PatchMapping("/{id}/ler")
        fun marcarComoLida(@PathVariable id: UUID): ResponseEntity<Notificacao> {
            val atualizada = notificacaoService.marcarComoLida(id)
            return if (atualizada != null) ResponseEntity.ok(atualizada)
            else ResponseEntity.notFound().build()
        }
    }

