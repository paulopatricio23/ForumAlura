package br.com.alura.forum.controller

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Arrays

@RestController //anotação para enviar e receber dados
@RequestMapping("/topicos") //Dizer qual endereço e qual o URI de trabalho deste controller
class TopicoController {

    @GetMapping //Quando chegar uma requisição get cai neste método
    fun listar(): List<Topico> {
        val topico = Topico(
            id = 1,
            titulo = "Duvida Kotlin",
            mensagem = "Variáveis no Kotlin",
            curso = Curso(
                id = 1,
                nome = "Kotlin",
                categoria = "Programação"
            ),
            autor = Usuario(
                id = 1,
                nome = "Paulo Patricio",
                email= "paulo@email.com"
            )
        )

        return Arrays.asList(topico, topico, topico)
    }
}