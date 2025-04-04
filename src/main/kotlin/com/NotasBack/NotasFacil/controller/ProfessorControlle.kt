package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.ProfessorRequest
import com.NotasBack.NotasFacil.model.Professor
import com.NotasBack.NotasFacil.service.ProfessorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/professores")
class ProfessorController(
    private val service: ProfessorService
) {
    @PostMapping
    fun criar(@RequestBody request: ProfessorRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request))

    @GetMapping
    fun listar() = ResponseEntity.ok(service.listar())

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: UUID) = ResponseEntity.ok(service.buscarPorId(id))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody request: ProfessorRequest) =
        ResponseEntity.ok(service.atualizar(id, request))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}

