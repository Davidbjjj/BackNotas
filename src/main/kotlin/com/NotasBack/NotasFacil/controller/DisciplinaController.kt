package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.*
import com.NotasBack.NotasFacil.model.Disciplina
import com.NotasBack.NotasFacil.service.DisciplinaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/disciplinas")
class DisciplinaController(private val service: DisciplinaService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarDisciplina(@Valid @RequestBody dto: DisciplinaDTO): DisciplinaResponseDTO {
        return toResponseDTO(service.criarDisciplina(dto))
    }

    @GetMapping
    fun listarTodas(): List<DisciplinaResponseDTO> {
        return service.listarTodas().map { toResponseDTO(it) }
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): DisciplinaResponseDTO {
        return toResponseDTO(service.buscarPorId(id))
    }

    @PutMapping("/{id}")
    fun atualizarDisciplina(
        @PathVariable id: UUID,
        @RequestBody dto: DisciplinaDTO
    ): DisciplinaResponseDTO {
        return toResponseDTO(service.atualizarDisciplina(id, dto))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarDisciplina(@PathVariable id: UUID) {
        service.deletarDisciplina(id)
    }

    // --- Associações ---
    @PostMapping("/{disciplinaId}/professor/{professorId}")
    fun associarProfessor(
        @PathVariable disciplinaId: UUID,
        @PathVariable professorId: UUID
    ): DisciplinaResponseDTO {
        return toResponseDTO(service.associarProfessor(disciplinaId, professorId))
    }

    @PostMapping("/{disciplinaId}/alunos/{alunoId}")
    fun matricularAluno(
        @PathVariable disciplinaId: UUID,
        @PathVariable alunoId: UUID
    ): DisciplinaResponseDTO {
        return toResponseDTO(service.matricularAluno(disciplinaId, alunoId))
    }

    @DeleteMapping("/{disciplinaId}/alunos/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun desmatricularAluno(
        @PathVariable disciplinaId: UUID,
        @PathVariable alunoId: UUID
    ) {
        service.desmatricularAluno(disciplinaId, alunoId)
    }

    @GetMapping("/{disciplinaId}/alunos")
    fun listarAlunos(@PathVariable disciplinaId: UUID): List<AlunoResponseDTO> {
        return service.listarAlunos(disciplinaId).map {
            AlunoResponseDTO(id = it.id, nome = it.nome)
        }
    }
    
    private fun toResponseDTO(disciplina: Disciplina): DisciplinaResponseDTO {
        return DisciplinaResponseDTO(
            id = disciplina.id,
            nome = disciplina.nome,
            professorNome = disciplina.professor.nome,
        )
    }
}
