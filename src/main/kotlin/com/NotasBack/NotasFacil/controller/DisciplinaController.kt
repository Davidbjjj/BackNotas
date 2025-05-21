package com.NotasBack.NotasFacil.controller

import com.NotasBack.NotasFacil.DTO.*
import com.NotasBack.NotasFacil.model.Disciplina
import com.NotasBack.NotasFacil.service.DisciplinaService
import com.NotasBack.NotasFacil.service.NotificacaoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/disciplinas")
class DisciplinaController(private val disciplinaService: DisciplinaService,  private val notificacaoService: NotificacaoService) {


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun criarDisciplina(@Valid @RequestBody dto: DisciplinaDTO): DisciplinaResponseDTO {
        return disciplinaService.criarDisciplina(dto)
    }


    @GetMapping
    fun listarTodas(): List<DisciplinaResponseDTO> {
        return disciplinaService.listarTodas().map { toResponseDTO(it) }
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): DisciplinaResponseDTO {
        return toResponseDTO(disciplinaService.buscarPorId(id))
    }

    @PutMapping("/{id}")
    fun atualizarDisciplina(
        @PathVariable id: UUID,
        @RequestBody dto: DisciplinaDTO
    ): DisciplinaResponseDTO {
        return toResponseDTO(disciplinaService.atualizarDisciplina(id, dto))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletarDisciplina(@PathVariable id: UUID) {
        disciplinaService.deletarDisciplina(id)
    }



    @PostMapping("/{disciplinaId}/alunos/{alunoId}")
    fun matricularAluno(
        @PathVariable disciplinaId: UUID,
        @PathVariable alunoId: UUID
    ): DisciplinaResponseDTO {
        return toResponseDTO(disciplinaService.matricularAluno(disciplinaId, alunoId))
    }

    @DeleteMapping("/{disciplinaId}/alunos/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun desmatricularAluno(
        @PathVariable disciplinaId: UUID,
        @PathVariable alunoId: UUID
    ) {
        disciplinaService.desmatricularAluno(disciplinaId, alunoId)
    }

    @GetMapping("/{disciplinaId}/alunos")
    fun listarAlunos(@PathVariable disciplinaId: UUID): List<AlunoResponseDTO> {
        return disciplinaService.listarAlunos(disciplinaId).map {
            AlunoResponseDTO(id = it.id, nome = it.nome)
        }
    }
    @PostMapping("/{disciplinaId}/notificar")
    fun notificarAlunos(
        @PathVariable disciplinaId: UUID,
        @RequestParam titulo: String,
        @RequestParam mensagem: String
    ): ResponseEntity<String> {
        val alunos = disciplinaService.listarAlunos(disciplinaId)
        if (alunos.isEmpty()) return ResponseEntity.badRequest().body("Nenhum aluno matriculado na disciplina.")

        alunos.forEach { aluno ->
            notificacaoService.criarNotificacao(titulo, mensagem, aluno.id)
        }

        return ResponseEntity.ok("Notificações enviadas para ${alunos.size} alunos.")
    }
    
    private fun toResponseDTO(disciplina: Disciplina): DisciplinaResponseDTO {
        return DisciplinaResponseDTO(
            id = disciplina.id,
            nome = disciplina.nome,
            professorNome = disciplina.professor.nome,
            professorEmail = disciplina.professor.email,
            escola = disciplina.escola?.nome ?:"Sem escola"
        )
    }
    @GetMapping("/escola/nome/{nome}")
    fun listarPorNomeEscola(@PathVariable nome: String): List<DisciplinaResponseDTO> {
        return disciplinaService.listarPorNomeEscola(nome).map { toResponseDTO(it) }
    }
    @PostMapping("/{nomeDisciplina}/associar-professor/{emailProfessor}")
    fun associarProfessor(
        @PathVariable nomeDisciplina: String,
        @PathVariable emailProfessor: String
    ): ResponseEntity<String> {
        val disciplina = disciplinaService.associarProfessor(nomeDisciplina, emailProfessor)
        return ResponseEntity.ok("Professor '${disciplina.professor?.nome}' associado à disciplina '${disciplina.nome}' com sucesso.")
    }

}
