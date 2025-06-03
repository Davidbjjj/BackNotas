package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.DisciplinaResponseDTO
import com.NotasBack.NotasFacil.DTO.EscolaLoginRequest
import com.NotasBack.NotasFacil.DTO.EscolaLoginResponse
import com.NotasBack.NotasFacil.DTO.EscolaRequest
import com.NotasBack.NotasFacil.service.AuthEscolaService
import com.NotasBack.NotasFacil.service.EscolaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/escolas")
class EscolaController (
    private val escolaService: EscolaService,
    private val authEscolaService: AuthEscolaService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: EscolaLoginRequest): ResponseEntity<EscolaLoginResponse> {
        val response = authEscolaService.login(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun criar (@RequestBody request: EscolaRequest) = ResponseEntity.status(HttpStatus.CREATED).body(escolaService.criar(request))

    @GetMapping
    fun listar() = ResponseEntity.ok(escolaService.listar())

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: UUID) = ResponseEntity.ok(escolaService.buscarPorId(id))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id:UUID, @RequestBody request: EscolaRequest) = ResponseEntity.ok(escolaService.atualizar(id, request))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        escolaService.deletar(id)
        return ResponseEntity.noContent().build()
    }
    // Endpoint para adicionar email permitido a uma escola pelo nome da escola
    @PostMapping("/{nomeEscola}/emails-permitidos")
    fun adicionarEmailPermitido(
        @PathVariable nomeEscola: String,
        @RequestParam email: String
    ): ResponseEntity<String> {
        return try {
            val mensagem =escolaService.adicionarEmailPermitidoEscola(nomeEscola, email)
            ResponseEntity.ok(mensagem)
        } catch (ex: NoSuchElementException) {
            ResponseEntity.badRequest().body(ex.message)
        } catch (ex: Exception) {
            ResponseEntity.status(500).body("Erro interno: ${ex.message}")
        }
    }
    // --- Associações ---
    @PostMapping("/{nome}/associar-professor/{email}")
    fun associarProfessorAEscola(
        @PathVariable nome: String,
        @PathVariable email: String
    ): ResponseEntity<String> {
        val mensagem = escolaService.associarProfessorAEscola(nome , email)
        return ResponseEntity.ok(mensagem)
    }
}
