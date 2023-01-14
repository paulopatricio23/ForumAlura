package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*

@Service //Com esta anotação é possível injetar esta classe em qualquer outra classe gerenciada pelo Spring
class TopicoService {

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