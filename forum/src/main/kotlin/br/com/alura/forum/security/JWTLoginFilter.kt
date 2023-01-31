package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTLoginFilter(
    private val authManager: AuthenticationManager,
    private val jwtUtil: JWTUtil
) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication { // Método herdado que tentará realizar a autenticação com as informações passadas pela request
        val (username, password) = ObjectMapper().readValue(request?.inputStream, Credentials::class.java) // Busca da própria request o username e o password
        println("U: $username; P: $password" )
        val token = UsernamePasswordAuthenticationToken(username, password) // Criará um token ainda não autenticado
        return authManager.authenticate(token) // Tentará autenticar o token com o username e o password
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) { // Método que servirá de resposta para uma autenticação bem sucedida
        val username = (authResult?.principal as UserDetails).username // Busca o nome do usuário através do UserDetails
        val token = jwtUtil.generateToken(username) // Gera um token JWT
        response?.addHeader("Authorization", "Bearer $token") // Adiciona o token no Header da Response
    }
}
