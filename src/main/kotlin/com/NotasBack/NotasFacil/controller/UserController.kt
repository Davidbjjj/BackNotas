package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/email/{email}")
    fun buscarPorEmail(@PathVariable email: String): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(userService.findUserByEmail(email))
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(mapOf(
                "mensagem" to e.message,
                "status" to HttpStatus.NOT_FOUND.value()
            ))
        }
    }
}