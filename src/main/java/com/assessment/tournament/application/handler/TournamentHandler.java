package com.assessment.tournament.application.handler;

import com.assessment.tournament.application.dto.TournamentListResponse;
import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;

import java.util.List;

public interface TournamentHandler {
    TournamentResponseDto saveTournament(TournamentRequestDto tournamentRequestDto, String token);
    TournamentListResponse getTournamentsByUserId(String token);
}
