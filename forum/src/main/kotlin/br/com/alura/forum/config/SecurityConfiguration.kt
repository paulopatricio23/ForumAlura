package br.com.alura.forum.config

import br.com.alura.forum.security.JWTAuthenticationFilter
import br.com.alura.forum.security.JWTLoginFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val configuration: AuthenticationConfiguration, // Como o WebSecurityconfigureradapter está depreciado, basta injetar esta dependencia para chamar o authenticationManager
    private val jwtUtil: JWTUtil
) {

    /*
    * Resposta do Fórum da Alura:
    *
    * "Implementei inicialmente a SecurityConfiguration como mostrado na aula, mas vi o alerta do Intellij de que a classe WebSecurityConfigurerAdapter está deprecated. Pesquisando na internet, descobri que a configuração do Spring Security foi simplificada. Aparentemente agora não é necessário configurar explicitamente o userDetailsService nem o PasswordEncrypter - basta ter na aplicação beans que implementem as respectivas interfaces.
    * Para o PasswordEncrypter implementei um bean na SecurityConfiguration, mas para o userDetailsService não foi necessário, pois como o UsuarioService é um Bean (pois está anotado com @Service) e implementa a interface UserDetailsService, o Spring Security já o usa automaticamente como userDetailsService."
    * */

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http?.
            csrf()?.disable()?. // Desabilitando o CSRF (Cross-Site Request Forgery) que consiste em requisições se passando por um usuário legítimo. É importante mantê-lo habilitado em ambiente de produção para evitar estes ataques. Aqui estamos desabilitando para fazer menos configurações que não são o alvo da aula
            authorizeHttpRequests()?.
            requestMatchers(HttpMethod.POST,"/login")?.permitAll()?. // Permitir todos os usuários
            anyRequest()?.authenticated()?. // Todas as requests precisam estar autenticadas
            and()
        http?.addFilterBefore(JWTLoginFilter(authManager = configuration.authenticationManager, jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass) // Filtro para interceptar as credenciais, autenticar e criar o token
        http?.addFilterBefore(JWTAuthenticationFilter(jwtUtil = jwtUtil), UsernamePasswordAuthenticationFilter().javaClass) // Filtro para validar o token por requisição
        http?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Não guardar status da autenticação
        return http.build()
    }

    @Bean
    fun encode(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }
}