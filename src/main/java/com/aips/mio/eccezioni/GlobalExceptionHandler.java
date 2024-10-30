package com.aips.mio.eccezioni;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.aips.mio.messages.MessageService;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    private final MessageService messageService;

    public GlobalExceptionHandler(MessageService messageService) {
        this.messageService = messageService;
    }
	
    // Gestisce errori di tipo NullPointerException, IllegalArgumentException, ecc.
    @ExceptionHandler({ NullPointerException.class, IllegalArgumentException.class })
    public ResponseEntity<?> handleBadRequestExceptions(Exception ex, WebRequest request, Locale locale) {
        String messaggioErrore = messageService.getMessaggio("bad_request_error", locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messaggioErrore);
    }
	
	
	// Gestione di EntityNotFoundException per gli errori 404
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request, Locale locale) {
	    String messaggioErrore = messageService.getMessaggio("resource_not_found", locale);
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messaggioErrore);
	}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Gestione di eccezioni non previste che provocano un errore 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request, Locale locale) {
        ex.printStackTrace();  // Questo aiuta a vedere l'errore completo nel log
        System.out.println("Errore rilevato: " + ex.getMessage());  // Log del messaggio d'errore
    	String messaggioErrore = messageService.getMessaggio("internal_server_error", locale);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messaggioErrore);
    }
}


