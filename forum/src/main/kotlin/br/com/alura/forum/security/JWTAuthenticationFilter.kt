package br.com.alura.forum.security

import br.com.alura.forum.config.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthenticationFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = request.getHeader("Authorization")
        val jwt = getTokenDetail(token) // Faz uma tratativa para ler apenas o token, retirando a String Bearer
        if (jwtUtil.isValid(jwt)) { // Verifica se o token é válido através do secret
            val authentication = jwtUtil.getAuthentication(jwt) // Busca a authentication para jogar no contexto da aplicação
            SecurityContextHolder.getContext().authentication = authentication // Joga a authentication no contexto
        }
        filterChain.doFilter(request, response)
    }

    private fun getTokenDetail(token: String?): String? {
        return token?.let { jwt ->
            jwt.startsWith("Bearer ")
            jwt.substring(7, jwt.length)
        }
    }

}
