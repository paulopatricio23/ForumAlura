package br.com.alura.forum.controller

import br.com.alura.forum.dto.AtualizacaoRespostaForm
import br.com.alura.forum.dto.NovaRespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.RespostaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/respostas")
class RespostaController(private val service: RespostaService) {

    @GetMapping
    fun listar(): List<Resposta> {
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): RespostaView {
        return service.buscarRespostaViewPorId(id)
    }

    @PostMapping()
    fun cadastrar(
        @RequestBody @Valid novaRespostaForm: NovaRespostaForm,
        uriBuilder: UriComponentsBuilder //Este uri builder servia para fornecer o uri completo do recurso de acordo com o ambiente de execução
    ): ResponseEntity<RespostaView> {
        val respostaView = service.cadastrar(novaRespostaForm)
        val uri = uriBuilder.path("/respostas/${respostaView.id}").build().toUri() //É uma boa prática retornar um uri para acessar este recurso criado e o próprio recurso
        return ResponseEntity.created(uri).body(respostaView)
    }

    @PutMapping()
    fun atualizar(@RequestBody @Valid atualizacaoRespostaForm: AtualizacaoRespostaForm) {
        val respostaView = service.atualizar(atualizacaoRespostaForm)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletar(id: Long) {
        service.deletar(id)
    }
}