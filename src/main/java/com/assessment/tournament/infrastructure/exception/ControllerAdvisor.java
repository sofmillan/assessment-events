package com.assessment.tournament.infrastructure.exception;

import com.assessment.tournament.domain.exception.FreeTournamentLimitException;
import com.assessment.tournament.domain.exception.TournamentSoldOutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(FreeTournamentLimitException.class)
    public ResponseEntity<ErrorResponse> freeTournamentLimitException(FreeTournamentLimitException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT, 409);
        return ResponseEntity.status(409).body(errorResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFoundException(DataNotFoundException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND, 404);
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(TournamentSoldOutException.class)
    public ResponseEntity<ErrorResponse> tournamentSoldOutException(TournamentSoldOutException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT, 409);
        return ResponseEntity.status(409).body(errorResponse);
    }
}
