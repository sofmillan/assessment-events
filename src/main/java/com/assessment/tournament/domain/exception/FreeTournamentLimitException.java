package com.assessment.tournament.domain.exception;

public class FreeTournamentLimitException extends RuntimeException{
    public FreeTournamentLimitException(String message) {
        super(message);
    }
}
