package br.com.alura.forum.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class NovoTopicoForm(
    @field:NotEmpty (message = "Título não pode ser em branco")
    @field:Size(min = 5, max = 100, message = "Título deve ter entre 5 e 100 caracteres")
    val titulo: String, //As anotações do bean validation deveriam estar no método get, mas como estão no construtor, precisa colocar a anotation @field
    @field:NotEmpty (message = "Mensagem não pode ser em branco")
    val mensagem: String,
    @field:NotNull
    val idCurso: Long,
    @field:NotNull
    val idAutor: Long
)
