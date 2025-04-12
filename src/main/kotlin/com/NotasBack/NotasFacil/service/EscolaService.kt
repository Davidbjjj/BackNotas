package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.EscolaRequest
import com.NotasBack.NotasFacil.repository.EscolaRepository
import com.NotasBack.NotasFacil.model.Escola
import java.util.*
import org.springframework.stereotype.Service

@Service
class EscolaService(
    private val repository: EscolaRepository
) {
    fun criar(request: EscolaRequest): Escola{
        val escola = Escola (
            nome = request.nome,
            senha = PasswordUtils.encode(request.senha),
            email = request.email,
            endereco = request.endereco
        )

        return repository.save(escola)

    }

    fun listar(): List<Escola> = repository.findAll()

    fun buscarPorId(id: UUID): Escola = repository.findById(id).orElseThrow {
        NoSuchElementException ("Escola não encontrada") }

    fun atualizar(id: UUID, request: EscolaRequest): Escola {
        val existente = buscarPorId(id)
        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            endereco = request.endereco
        )
        return repository.save(atualizado)
    }

    fun deletar(id: UUID) = repository.deleteById(id)
}