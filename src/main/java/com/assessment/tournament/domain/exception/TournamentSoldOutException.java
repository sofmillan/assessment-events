package com.assessment.tournament.domain.exception;

public class TournamentSoldOutException extends RuntimeException{
    public TournamentSoldOutException(String message) {
        super(message);
    }
}
