package br.com.alura.forum.config

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
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
            .signWith(SignatureAlgorithm.ES512, secret.toByteArray())
            .compact()
    }
}