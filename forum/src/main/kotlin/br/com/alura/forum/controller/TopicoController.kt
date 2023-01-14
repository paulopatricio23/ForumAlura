package br.com.alura.forum.controller

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import br.com.alura.forum.service.TopicoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays

@RestController //anotação para enviar e receber dados
@RequestMapping("/topicos") //Dizer qual endereço e qual o URI de trabalho deste controller
class TopicoController (private val service: TopicoService) {

    @GetMapping //Quando chegar uma requisição get cai neste método
    fun listar(): List<Topico> {
       return service.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Topico {
        return service.buscarPorId(id)
    }

    @PostMapping
    fun cadastrar(@RequestBody topico: Topico) {
        service.cadastrar(topico)
    }

}