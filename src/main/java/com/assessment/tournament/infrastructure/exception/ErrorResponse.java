package com.assessment.tournament.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@RequiredArgsConstructor

public class ErrorResponse {
    private final String errorMessage;
    private final LocalDateTime dateTime;
    private final HttpStatus status;
    private final Integer statusCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
