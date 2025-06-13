package com.NotasBack.NotasFacil.controller


import com.NotasBack.NotasFacil.DTO.LoginRequest
import com.NotasBack.NotasFacil.DTO.LoginResponse
import com.NotasBack.NotasFacil.DTO.ProfessorRequest
import com.NotasBack.NotasFacil.DTO.ProfessorResponseDTO
import com.NotasBack.NotasFacil.service.AuthService
import com.NotasBack.NotasFacil.service.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/professores")
class ProfessorController(
    private val professorService: ProfessorService,
    private val authService: AuthService

) {

    @PostMapping
    fun criar(@RequestBody request: ProfessorRequest): ResponseEntity<ProfessorResponseDTO> {
        val professorCriado = professorService.criar(request)
        return ResponseEntity.ok(professorCriado)
    }

    @GetMapping("/por-email/{email}")
    fun buscarPorEmail(@PathVariable email: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(professorService.buscarPorEmail(email))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf(
                "mensagem" to e.message,
                "status" to HttpStatus.NOT_FOUND.value()
            ))
        }
    }

    @GetMapping
    fun listar() = ResponseEntity.ok(professorService.listar())

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: UUID) = ResponseEntity.ok(professorService.buscarPorId(id))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody request: ProfessorRequest) =
        ResponseEntity.ok(professorService.atualizar(id, request))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        professorService.deletar(id)
        return ResponseEntity.noContent().build()
    }
}


