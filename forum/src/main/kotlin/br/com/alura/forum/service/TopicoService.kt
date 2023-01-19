package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Service //Com esta anotação é possível injetar esta classe em qualquer outra classe gerenciada pelo Spring
class TopicoService(
    private var topicos: List<Topico> = ArrayList(),
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper
) {

    fun listar(): List<TopicoView> {
        return topicos.stream().map {
            t -> topicoViewMapper.map(t)
        }.collect(Collectors.toList()) // Converter de stream para uma lista
    }

    fun buscarTopicoViewPorId(id: Long): TopicoView {
        val topico = topicos.stream().filter { t ->
            t.id == id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
        return topicoViewMapper.map(topico)
    }

    fun buscarPorId(id: Long): Topico {
        return topicos.stream().filter { t ->
            t.id == id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm) {
        val topico = topicoFormMapper.map(novoTopicoForm)
        topico.id = topicos.size.toLong() + 1
        topicos = topicos.plus(topico)
    }

    fun atualizar(atualizacaoTopicoForm: AtualizacaoTopicoForm) {
        val topico = topicos.stream().filter { t ->
            t.id == atualizacaoTopicoForm.id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
        topicos = topicos.minus(topico).plus(Topico(
            id = atualizacaoTopicoForm.id,
            titulo = atualizacaoTopicoForm.titulo,
            mensagem = atualizacaoTopicoForm.mensagem,
            autor = topico.autor,
            curso = topico.curso,
            respostas = topico.respostas,
            status = topico.status,
            dataCriacao = topico.dataCriacao
        ))
    }

    fun deletar(id: Long) {
        val topico = topicos.stream().filter { t ->
            t.id == id //dado o topico (t), eu quero o topico que tiver o id que foi passado no parâmetro
        }.findFirst().get()
        topicos = topicos.minus(topico)
    }
}