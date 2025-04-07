package com.NotasBack.NotasFacil.service

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

object PasswordUtils {
    private val encoder = BCryptPasswordEncoder()

    fun encode(password: String): String {
        return encoder.encode(password)
    }
}
