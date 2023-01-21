package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AtualizacaoRespostaForm (
    @field:NotNull
    val id: Long,
    @field:NotEmpty
    val mensagem: String,
    val solucao: Boolean
)
