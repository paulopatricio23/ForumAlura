package br.com.alura.forum.controller

import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.RespostaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/respostas")
class RespostaController(private val service: RespostaService) {

    @GetMapping
    fun listar(): List<Resposta> {
        return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Resposta {
        return service.buscarPorId(id)
    }
}