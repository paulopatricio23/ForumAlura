package br.com.alura.forum.exception

import br.com.alura.forum.dto.ErrorView
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
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

    @ExceptionHandler(Exception::class) // Informa ao Spring que quando qualquer exception acontecer, este método será executado
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // O ResponseStatus a ser devolvido
    fun handleServerError(
        exception: Exception,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class) // Informa ao Spring que quando a exception MethodArgumentNotValidException (erro de validação do bean) acontecer, este método será executado
    @ResponseStatus(HttpStatus.BAD_REQUEST) // O ResponseStatus a ser devolvido
    fun handleValidationError(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ErrorView {
        val errorMessage = HashMap<String, String?>() // Variável que vai conter um map com todos os erros de forma simplificada
        exception.bindingResult.fieldErrors.forEach{ // Percorrer todos os erros vindos na exception
            e ->
            errorMessage[e.field] = e.defaultMessage // Adicionando apenas a mensagem do erro. Esta mensagem pode ser personalizada na configuração do bean no data class
        }
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }
}