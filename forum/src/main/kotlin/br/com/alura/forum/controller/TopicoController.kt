package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.service.TopicoService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController //anotação para enviar e receber dados
@RequestMapping("/topicos") //Dizer qual endereço e qual o URI de trabalho deste controller
class TopicoController (private val service: TopicoService) {

    @GetMapping //Quando chegar uma requisição get cai neste método
    fun listar(@RequestParam(required = false) nomeCurso: String?): List<TopicoView> {
        return service.listar(nomeCurso)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView {
        return service.buscarTopicoViewPorId(id)
    }

    @PostMapping
    @Transactional // Anotation do contexto de transação para fazer o commit apenas no final da transação
    fun cadastrar(
        @RequestBody @Valid novoTopicoForm: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder //Este uri builder servia para fornecer o uri completo do recurso de acordo com o ambiente de execução
    ): ResponseEntity<TopicoView> {
        val topicoView = service.cadastrar(novoTopicoForm)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri() //É uma boa prática retornar um uri para acessar este recurso criado e o próprio recurso
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional // Anotation do contexto de transação para fazer o commit apenas no final da transação
    fun atualizar(@RequestBody @Valid atualizacaoTopicoForm: AtualizacaoTopicoForm): ResponseEntity<TopicoView> {
        val topicoView = service.atualizar(atualizacaoTopicoForm)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @Transactional // Anotation do contexto de transação para fazer o commit apenas no final da transação
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(@PathVariable id: Long) {
        service.deletar(id)
    }

}