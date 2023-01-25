package br.com.alura.forum.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfiguration() {

    /*
    * Resposta do Fórum da Alura:
    *
    * "Implementei inicialmente a SecurityConfiguration como mostrado na aula, mas vi o alerta do Intellij de que a classe WebSecurityConfigurerAdapter está deprecated. Pesquisando na internet, descobri que a configuração do Spring Security foi simplificada. Aparentemente agora não é necessário configurar explicitamente o userDetailsService nem o PasswordEncrypter - basta ter na aplicação beans que implementem as respectivas interfaces.
    * Para o PasswordEncrypter implementei um bean na SecurityConfiguration, mas para o userDetailsService não foi necessário, pois como o UsuarioService é um Bean (pois está anotado com @Service) e implementa a interface UserDetailsService, o Spring Security já o usa automaticamente como userDetailsService."
    * */

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http?.
            authorizeHttpRequests()?.anyRequest()?.authenticated()?. // Todas as requests precisam estar autenticadas
            and()?.sessionManagement()?.sessionCreationPolicy(SessionCreationPolicy.STATELESS)?. // Não guardar status da autenticação
            and()?.formLogin()?.disable()?.httpBasic() // Desativar a tela de login do próprio Spring ao acessar a aplicação
            return http.build()
    }

    @Bean
    fun encode(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}