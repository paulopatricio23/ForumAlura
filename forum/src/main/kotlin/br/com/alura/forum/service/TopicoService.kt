package br.com.alura.forum.service

import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Service //Com esta anotação é possível injetar esta classe em qualquer outra classe gerenciada pelo Spring
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val cursoService: CursoService,
    private val usuarioService: UsuarioService
) {

    fun listar(): List<TopicoView> {
        return topicos.stream().map { t -> TopicoView(
            id =  t.id,
            titulo = t.titulo,
            mensagem = t.mensagem,
            dataCriacao = t.dataCriacao,
            status =  t.status
        ) }.collect(Collectors.toList()) // Converter de stream para uma lista
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = topicos.stream().filter { t ->
            t.id == id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
        return TopicoView(
            id =  topico.id,
            titulo = topico.titulo,
            mensagem = topico.mensagem,
            dataCriacao = topico.dataCriacao,
            status =  topico.status
        )
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm) {
        topicos = topicos.plus(Topico(
            id = topicos.size.toLong() + 1,
            titulo = novoTopicoForm.titulo,
            mensagem = novoTopicoForm.mensagem,
            curso = cursoService.buscarPorId(novoTopicoForm.idCurso),
            autor = usuarioService.buscarPorId(novoTopicoForm.idAutor)
        ))
    }
}