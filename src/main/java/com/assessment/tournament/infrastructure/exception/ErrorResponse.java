package com.assessment.tournament.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor

public class ErrorResponse {

    private final LocalDateTime dateTime;
    private final HttpStatus status;
    private final Integer statusCode;
    private final List<String> errorMessages;

}
