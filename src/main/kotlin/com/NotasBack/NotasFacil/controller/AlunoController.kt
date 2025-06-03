package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.AlunoLoginRequest
import com.NotasBack.NotasFacil.DTO.AlunoLoginResponse
import com.NotasBack.NotasFacil.DTO.AlunoRequest
import com.NotasBack.NotasFacil.service.AlunoService
import com.NotasBack.NotasFacil.service.AuthAlunoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/alunos")
class AlunoController(
    private val service: AlunoService,
    private val authService: AuthAlunoService

) {
    @PostMapping("/login")
    fun login(@RequestBody request: AlunoLoginRequest): ResponseEntity<AlunoLoginResponse> {
        val response = authService.login(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun criar(@RequestBody request: AlunoRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request))

    @GetMapping
    fun listar() = ResponseEntity.ok(service.listar())

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: UUID) = ResponseEntity.ok(service.buscarPorId(id))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody request: AlunoRequest) =
        ResponseEntity.ok(service.atualizar(id, request))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}

