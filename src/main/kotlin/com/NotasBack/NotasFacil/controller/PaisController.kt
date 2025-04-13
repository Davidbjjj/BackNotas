package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.PaisRequest
import com.NotasBack.NotasFacil.service.PaisService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@Controller
@RequestMapping("/pais")
class PaisController(private val service: PaisService) {
    @PostMapping
    fun criar(@RequestBody request: PaisRequest) =
        ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request))

    @GetMapping
    fun listar() = ResponseEntity.ok(service.listar())

    @GetMapping("/{id}")
    fun buscar(@PathVariable id: UUID) = ResponseEntity.ok(service.buscarPorId(id))

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody request: PaisRequest) =
        ResponseEntity.ok(service.atualizar(id, request))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}