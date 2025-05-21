package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.EscolaRequest
import com.NotasBack.NotasFacil.repository.EscolaRepository
import com.NotasBack.NotasFacil.model.Escola
import com.NotasBack.NotasFacil.model.Professor
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import jakarta.transaction.Transactional
import java.util.*
import org.springframework.stereotype.Service

@Service
class EscolaService(
    private val repository: EscolaRepository,
    private val professorRepository: ProfessorRepository,
    private val escolaRepository: EscolaRepository
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

    fun associarProfessorAEscola(nome: String, email: String): String {
        val escola = repository.findByNome(nome)
            .orElseThrow { NoSuchElementException("Escola com nome $nome não encontrada") }

        // Adiciona o e-mail à lista de permitidos, se ainda não estiver presente
        if (!escola.emailsPermitidos.contains(email)) {
            escola.emailsPermitidos.add(email)
            repository.save(escola)
        }

        return "E-mail '$email' foi adicionado à lista de e-mails permitidos da escola '${escola.nome}'."
    }



    @Transactional
    fun adicionarEmailPermitidoEscola(nomeEscola: String, email: String): String {
        val escola = escolaRepository.findByNome(nomeEscola)
            .orElseThrow { NoSuchElementException("Escola com nome '$nomeEscola' não encontrada") }

        // Cria uma cópia mutável do Set, pois Set original é imutável
        val emailsAtualizados = escola.emailsPermitidos.toMutableSet()

        // Verifica se o email já está na lista
        if (emailsAtualizados.contains(email)) {
            return "O email '$email' já está na lista de emails permitidos da escola '${escola.nome}'."
        }

        // Adiciona o novo email permitido
        emailsAtualizados.add(email)

        // Como Escola é data class e emailsPermitidos é val, cria uma cópia com novo Set
        val escolaAtualizada = escola.copy(emailsPermitidos = emailsAtualizados)

        // Salva a escola atualizada
        escolaRepository.save(escolaAtualizada)

        return "Email '$email' adicionado à lista de emails permitidos da escola '${escola.nome}'."
    }

}