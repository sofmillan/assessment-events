package com.assessment.tournament.infrastructure.exception;

import com.assessment.tournament.domain.exception.FreeTournamentLimitException;
import com.assessment.tournament.domain.exception.TournamentSoldOutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(FreeTournamentLimitException.class)
    public ResponseEntity<ErrorResponse> freeTournamentLimitException(FreeTournamentLimitException e){
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.CONFLICT, 409, List.of(e.getMessage()));
        return ResponseEntity.status(409).body(errorResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundException(DataNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse( LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, List.of(e.getMessage()));
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(TournamentSoldOutException.class)
    public ResponseEntity<ErrorResponse> tournamentSoldOutException(TournamentSoldOutException e){
        ErrorResponse errorResponse = new ErrorResponse( LocalDateTime.now(), HttpStatus.CONFLICT, 409, List.of(e.getMessage()));
        return ResponseEntity.status(409).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST,
                400,
                errors
        );

        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> internalServerError(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, 500, List.of("An unexpected error occurred"));
        return ResponseEntity.status(500).body(errorResponse);
    }
}
