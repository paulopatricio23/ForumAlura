package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class RespostaService(private var respostas: List<Resposta>) {

    init {
        val resposta = Resposta(
            id = 1,
            mensagem = "Resposta 1",
            autor = Usuario(
                id = 1,
                nome = "usuario 2",
                email= "usuario2@email.com"
            ),
            topico = Topico(
                id = 3,
                titulo = "Duvida Kotlin 3",
                mensagem = "Variáveis no Kotlin 3",
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
            ),
            solucao = true
        )
        val resposta2 = Resposta(
            id = 2,
            mensagem = "Resposta 2",
            autor = Usuario(
                id = 1,
                nome = "usuario 3",
                email= "usuario3@email.com"
            ),
            topico = Topico(
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
            ),
            solucao = true
        )
        respostas = listOf(resposta, resposta2)
    }

    fun listar(): List<Resposta> {
        return respostas
    }

    fun buscarPorId(id: Long): Resposta {
        return respostas.stream().filter{ r ->
            r.id == id
        }.findFirst().get()
    }


}
