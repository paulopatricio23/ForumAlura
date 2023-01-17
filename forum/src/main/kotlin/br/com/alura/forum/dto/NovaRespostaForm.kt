package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NovaRespostaForm (
    @field:NotEmpty
    @field:Size(min = 5, max = 1000)
    val mensagem: String,
    @field:NotNull
    val idAutor: Long,
    @field:NotNull
    val idTopic: Long,
    @field:NotNull
    val solucao: Boolean
)
