package com.NotasBack.NotasFacil.service

import com.NotasBack.NotasFacil.DTO.PaisRequest
import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.model.Pais
import com.NotasBack.NotasFacil.repository.AlunoRepository
import com.NotasBack.NotasFacil.repository.PaisRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.NoSuchElementException


@Service
class PaisService (private val paisRepository: PaisRepository, private val alunoRepository: AlunoRepository) {

    fun criar(request : PaisRequest) : Pais {

        val pais = Pais(
          nome = request.nome,
          email = request.email,
          senha = PasswordUtils.encode(request.senha),
          filhos = filhosDTOparafilhos(request.filhos)
        )
        return paisRepository.save(pais)
    }


    fun filhosDTOparafilhos(filhosUID: List<UUID>) : List<Aluno> {
        val  filhos :  ArrayList<Aluno> = arrayListOf()
        if (filhosUID.isEmpty()) return filhos
        for (f in filhosUID ){
               filhos.add(alunoRepository.findById(f).orElseThrow {NoSuchElementException("filhos não encontrados")})
        }
        return filhos
    }

    fun listar(): List<Pais> = paisRepository.findAll()

    fun buscarPorId(id: UUID): Pais = paisRepository.findById(id).orElseThrow {
        NoSuchElementException("Professor não encontrado")
    }

    fun atualizar(id: UUID, request: PaisRequest): Pais {
        val existente = buscarPorId(id)
        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
            filhos = filhosDTOparafilhos(request.filhos)
        )
        return paisRepository.save(atualizado)
    }

    fun deletar(id: UUID) {
        paisRepository.deleteById(id)
    }

}