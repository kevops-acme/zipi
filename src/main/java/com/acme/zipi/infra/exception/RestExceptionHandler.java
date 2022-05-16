package com.acme.zipi.infra.exception;

import java.util.UUID;

import com.acme.zipi.domain.exceptions.UserNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
    
  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<ErrorDTO> handleEntityNotFoundException(
      Exception ex, WebRequest request) {
    return buildResponseError(ex, HttpStatus.NOT_FOUND);
  }

    
    private ErrorDTO buildErrorDTO(Exception ex, HttpStatus status) {
        UUID code = UUID.randomUUID();
        log.error("An error has occurred - {}", code);
        log.info("message - {}", ex.getMessage());
    
        return ErrorDTO.builder()
            .code(code)
            .message(ex.getMessage())
            .status(status.value())
            .build();
      }
    
      private ResponseEntity<ErrorDTO> buildResponseError(Exception ex, HttpStatus status) {
        log.info("Message - {}", ex.getMessage());
        return new ResponseEntity<>(buildErrorDTO(ex, status), status);
      }

}
