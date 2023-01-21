package br.com.alura.forum.repository

import br.com.alura.forum.model.Topico
import org.springframework.data.jpa.repository.JpaRepository

interface TopicoRepository: JpaRepository<Topico, Long> { // Herda a classe JpaRepository indicando qual a entidade a ser trabalhada e o seu tipo do id/chave primária.
}