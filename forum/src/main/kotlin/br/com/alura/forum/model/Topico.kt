package br.com.alura.forum.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity // Com esta anotation a JPA já conseguirá entender que esta classe é uma entidade
data class Topico(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Como o próprio banco gerenciará od ids é necessário adicionar esta configuração
    var id: Long? = null,
    var titulo: String,
    var mensagem: String,
    val dataCriacao: LocalDateTime = LocalDateTime.now(),
    @ManyToOne // Anotation necessária para indicar que este atributo é uma relação com outra entidade e qual tipo desta relação
    val curso: Curso,
    @ManyToOne
    val autor: Usuario,
    @Enumerated(value = EnumType.STRING) // Para salvar no banco a String do Enum e não o index
    val status: StatusTopico = StatusTopico.NAO_RESPONDIDO,
    @OneToMany(mappedBy = "topico") // Necessário indicar qual o campo representado na outra entidade
    val respostas: List<Resposta> = ArrayList(),
    var dataAlteracao: LocalDate? = null
)
