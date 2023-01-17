package br.com.alura.forum.service

import br.com.alura.forum.dto.NovaRespostaForm
import br.com.alura.forum.dto.RespostaView
import br.com.alura.forum.mapper.RespostaFormMapper
import br.com.alura.forum.mapper.RespostaViewMapper
import br.com.alura.forum.model.Curso
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.model.Topico
import br.com.alura.forum.model.Usuario
import org.springframework.stereotype.Service

@Service
class RespostaService(
    private var respostas: List<Resposta>,
    private val respostaFormMapper: RespostaFormMapper,
    private val respostaViewMapper: RespostaViewMapper
) {

    fun listar(): List<Resposta> {
        return respostas
    }

    fun buscarRespostaViewPorId(id: Long): RespostaView {
        val resposta = respostas.stream().filter{ r ->
            r.id == id
        }.findFirst().get()
        return respostaViewMapper.map(resposta)
    }

    fun buscarPorId(id: Long): Resposta {
        return respostas.stream().filter{ r ->
            r.id == id
        }.findFirst().get()
    }

    fun cadastrar(novaRespostaForm: NovaRespostaForm) {
        var resposta = respostaFormMapper.map(novaRespostaForm)
        resposta.id = respostas.size.toLong() + 1
        respostas = respostas.plus(resposta)
    }


}
