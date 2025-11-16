package com.newhorizon_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ResponseErrorDto> handleNotFloudException(NotFoundException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ResponseErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Extrai os erros de validação
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "Campo '" + fieldError.getField() + "': " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        // Constrói a mensagem final
        String errorMessage = "A solicitação contém campos inválidos: " + errors;

        return buildErrorResponse(errorMessage, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    private ResponseEntity<ResponseErrorDto> handleUnsupportedOperationException(UnsupportedOperationException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, request);
    }

    @ExceptionHandler(ResponseStatusException.class)
    private ResponseEntity<ResponseErrorDto> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
        return buildErrorResponse(ex.getReason(), HttpStatus.valueOf(ex.getStatusCode().value()), request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    private ResponseEntity<ResponseErrorDto> handleNoResourceFoundException(NoResourceFoundException ex, HttpServletRequest request) {
        String message = "Recurso não encontrado: " + ex.getResourcePath();
        return buildErrorResponse(message, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ResponseErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Erro de integridade de dados: Não foi possível processar a operação devido a restrições do banco de dados.";
        
        // Tenta identificar o tipo de violação
        String exceptionMessage = ex.getMessage();
        if (exceptionMessage != null) {
            if (exceptionMessage.contains("Referential integrity constraint violation")) {
                message = "Erro de integridade referencial: Não é possível realizar esta operação pois existem dados relacionados ou a referência não existe.";
            } else if (exceptionMessage.contains("Duplicate") || exceptionMessage.contains("unique constraint")) {
                message = "Erro de duplicação: Já existe um registro com estes dados.";
            } else if (exceptionMessage.contains("NULL not allowed") || exceptionMessage.contains("not-null")) {
                message = "Erro de validação: Campos obrigatórios não foram preenchidos.";
            }
        }
        
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ResponseErrorDto> buildErrorResponse(String message, HttpStatus status, HttpServletRequest request) {
        return ResponseEntity.status(status)
                .body(new ResponseErrorDto(
                        LocalDateTime.now(),
                        status.value(),
                        message,
                        request.getRequestURI()
                ));
    }
}
