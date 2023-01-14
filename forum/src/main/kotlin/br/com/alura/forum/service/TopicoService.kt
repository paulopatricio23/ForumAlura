package br.com.alura.forum.service

import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service //Com esta anotação é possível injetar esta classe em qualquer outra classe gerenciada pelo Spring
class TopicoService(private var topicos: List<Topico> = ArrayList()) {

    fun listar(): List<Topico> {
        return topicos
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.stream().filter { t ->
            t.id == id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
    }

    fun cadastrar(topico: Topico) {
        topicos.plus(topico)
    }
}