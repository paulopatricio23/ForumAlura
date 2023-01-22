package br.com.alura.forum.repository

import br.com.alura.forum.dto.TopicoPorCategoriaDTO
import br.com.alura.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface TopicoRepository: JpaRepository<Topico, Long> { // Herda a classe JpaRepository indicando qual a entidade a ser trabalhada e o seu tipo do id/chave primária.

    fun findByCursoNome( // O JPA consegue entender que curso é uma relação e vai na tabela de curso filtrar o nome
        nomeCurso: String,
        paginacao: Pageable
    ): Page<Topico> // Ao utilizar paginação tem que trocar o retorno de List para Page

    @Query("SELECT new br.com.alura.forum.dto.TopicoPorCategoriaDTO(curso.categoria, count(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria") // Passando um SQL personalizado para consulta no banco
    // Neste caso foi utilizado um JPQL, mas também pode-se utilizar o parametro 'native = true' para evitar transformar uma consulta pré-existente em JPQL e utilizá-la sem alterá-la
    fun relatorio(): List<TopicoPorCategoriaDTO>
}