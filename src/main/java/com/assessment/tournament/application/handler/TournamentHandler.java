package com.assessment.tournament.application.handler;

import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;

public interface TournamentHandler {
    TournamentResponseDto saveTournament(TournamentRequestDto tournamentRequestDto, String token);
}
