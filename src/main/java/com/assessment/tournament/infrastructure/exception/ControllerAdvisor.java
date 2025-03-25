package com.assessment.tournament.infrastructure.exception;

import com.assessment.tournament.domain.exception.FreeTournamentLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(FreeTournamentLimitException.class)
    public ResponseEntity<ErrorResponse> resourceAlreadyExist(FreeTournamentLimitException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT, 409);
        return ResponseEntity.status(HttpStatusCode.valueOf(409)).body(errorResponse);
    }
}
