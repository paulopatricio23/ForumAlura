package br.com.alura.forum.mapper

import br.com.alura.forum.dto.NovaRespostaForm
import br.com.alura.forum.model.Resposta
import br.com.alura.forum.service.TopicoService
import br.com.alura.forum.service.UsuarioService

class RespostaFormMapper(
    private val usuarioService: UsuarioService,
    private val topicoService: TopicoService
): Mapper<NovaRespostaForm, Resposta> {

    override fun map(novaRespostaForm: NovaRespostaForm): Resposta {
        return Resposta(
            mensagem = novaRespostaForm.mensagem,
            autor = usuarioService.buscarPorId(novaRespostaForm.idAutor),
            topico = topicoService.buscarPorId(novaRespostaForm.idTopic),
            solucao = novaRespostaForm.solucao
        )
    }

}
