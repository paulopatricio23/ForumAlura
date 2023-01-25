package br.com.alura.forum.service

import br.com.alura.forum.model.Usuario
import br.com.alura.forum.repository.UsuarioRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private var repository: UsuarioRepository
    ) : UserDetailsService { // UserDetailsService é a classe que vai gerenciar a autenticação do usuário

    fun buscarPorId(id: Long): Usuario {
        return repository.getReferenceById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails { // Este método é herdado da classe UserDetailsService e servirá para utilizar a Entidade Usuario para autenticação
        val usuario = repository.findByEmail(username) ?: throw java.lang.RuntimeException()
        return UserDetail(usuario)
    }

}
