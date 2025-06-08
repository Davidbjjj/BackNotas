package com.NotasBack.NotasFacil.service
import com.NotasBack.NotasFacil.DTO.ProfessorRequest
import com.NotasBack.NotasFacil.DTO.ProfessorResponseDTO
import com.NotasBack.NotasFacil.model.Professor
import com.NotasBack.NotasFacil.model.Disciplina
import com.NotasBack.NotasFacil.repository.ProfessorRepository
import com.NotasBack.NotasFacil.repository.DisciplinaRepository
import com.NotasBack.NotasFacil.repository.EscolaRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProfessorService(
    private val repository: ProfessorRepository,
    private val disciplinaRepository: DisciplinaRepository,
    private val escolaRepository: EscolaRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun criar(request: ProfessorRequest): ProfessorResponseDTO {
        val escola = escolaRepository.findByNome(request.escolaNome)
            .orElseThrow { NoSuchElementException("Escola '${request.escolaNome}' não encontrada") }

        if (!escola.emailsPermitidos.contains(request.email)) {
            throw IllegalArgumentException("O e-mail '${request.email}' não está autorizado para a escola '${escola.nome}'")
        }


        val professor = Professor(
            nome = request.nome,
            email = request.email,
            senha = passwordEncoder.encode(request.senha),
            escola = escola
        )

        val salvo = repository.save(professor)

        return toResponseDTO(salvo)
    }


    fun listar(): List<ProfessorResponseDTO> {
        return repository.findAll().map { toResponseDTO(it) }
    }


    fun buscarPorId(id: UUID): Professor = repository.findById(id).orElseThrow {
        NoSuchElementException("Professor não encontrado")
    }

    fun atualizar(id: UUID, request: ProfessorRequest): Professor {

        val existente = buscarPorId(id)

        val atualizado = existente.copy(
            nome = request.nome,
            email = request.email,
            senha = PasswordUtils.encode(request.senha),
        )

        return repository.save(atualizado)
    }

    fun deletar(id: UUID) {
        repository.deleteById(id)
    }
    fun toResponseDTO(professor: Professor): ProfessorResponseDTO {
        return ProfessorResponseDTO(
            id = professor.id,
            nome = professor.nome,
            email = professor.email,
            role = professor.role,
            escolaNome = professor.escola?.nome,
            disciplinas = professor.disciplinas.map { it.nome }
        )
    }

}
