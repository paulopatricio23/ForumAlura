package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTUtil {

    private val expiration: Long = 60000 //Indica o tempo para expiração do token

    @Value("\${jwt.secret}") //Este valor será inicializado no momento em que a aplicação subir e com o valor definido no arquivo application.yml
    private lateinit var secret: String

    fun generateToken(username: String): String? {
        return Jwts.builder() // Builder do token
            .setSubject(username) // Como precisamos passar as informações do usuário, aqui passamos o nome
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            return true
        } catch (e: java.lang.IllegalArgumentException) {
            return true
        }
    }

    fun getAuthentication(jwt: String?): Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject // Para ler o username no JWT
        return UsernamePasswordAuthenticationToken(username, null, null) // Não é necessário passar as credentials pois já sabemos que está logado, nem as autorities pois ainda não estamos trabalhando com elas
    }
}