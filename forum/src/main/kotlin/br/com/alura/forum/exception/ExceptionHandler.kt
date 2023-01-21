package br.com.alura.forum.exception

import br.com.alura.forum.dto.ErrorView
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice // Informar ao Spring que a classe trabalha com tratatmento de Exceptions. Esta classe faz um tratamento com RestControllers
class ExceptionHandler {

    @ExceptionHandler(NotFoundException::class) // Informa ao Spring que quando a exception NotFoundException acontecer, este método será executado
    @ResponseStatus(HttpStatus.NOT_FOUND) // O ResponseStatus a ser devolvido
    fun handleNotFound(
        exception: NotFoundException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message,
            path = request.servletPath
        )
    }
}