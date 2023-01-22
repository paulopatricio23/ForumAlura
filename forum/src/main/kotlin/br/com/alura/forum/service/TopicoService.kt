package br.com.alura.forum.service

import br.com.alura.forum.dto.AtualizacaoTopicoForm
import br.com.alura.forum.dto.NovoTopicoForm
import br.com.alura.forum.dto.TopicoView
import br.com.alura.forum.exception.NotFoundException
import br.com.alura.forum.mapper.TopicoFormMapper
import br.com.alura.forum.mapper.TopicoViewMapper
import br.com.alura.forum.model.Topico
import br.com.alura.forum.repository.TopicoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import kotlin.collections.ArrayList

@Service //Com esta anotação é possível injetar esta classe em qualquer outra classe gerenciada pelo Spring
class TopicoService(
    private var repository: TopicoRepository,
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Topico não encontrado!"
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        val topicos = if (nomeCurso == null) {
            repository.findAll(paginacao)
        } else {
            repository.findByCursoNome(nomeCurso, paginacao)
        }
//        return topicos.stream().map {
//            t -> topicoViewMapper.map(t)
//        }.collect(Collectors.toList()) // Converter de stream para uma lista
        return topicos.map {
            t -> topicoViewMapper.map(t)
        } // Com  a mudança para Page, não precisa utilizar o Stream e nem o Collector
    }

    fun buscarTopicoViewPorId(id: Long): TopicoView {
        val topico = repository.findById(id)
            .orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun buscarPorId(id: Long): Topico {
        return repository.findById(id)
            .orElseThrow{NotFoundException(notFoundMessage)}
    }

    fun cadastrar(novoTopicoForm: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(novoTopicoForm)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(atualizacaoTopicoForm: AtualizacaoTopicoForm): TopicoView {
        val topico = repository.findById(atualizacaoTopicoForm.id).orElseThrow{NotFoundException(notFoundMessage)}
        topico.titulo = atualizacaoTopicoForm.titulo
        topico.mensagem = atualizacaoTopicoForm.mensagem
        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }
}