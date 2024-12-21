package com.example.backend.configuraciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception e) {
        // Log the exception for debugging purposes
        e.printStackTrace();
        
        // Devuelve una respuesta de error con un mensaje descriptivo
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Ocurrió un error interno en el servidor. Por favor, inténtalo de nuevo más tarde.");
    }
}
