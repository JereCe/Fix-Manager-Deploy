package com.equipo1.fix_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> manejarNotFound(IllegalArgumentException e) {
        return generarError(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> manejarConflict(IllegalStateException e) {
        return generarError(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> manejarGenerico(Exception e) {
        return generarError("Error inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> manejarUnauthorized(UnauthorizedException e) {
        return generarError(e.getMessage(), HttpStatus.UNAUTHORIZED); // 401
    }

    private ResponseEntity<?> generarError(String mensaje, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", mensaje);
        body.put("codigo", status.value());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(body);
    }
}
