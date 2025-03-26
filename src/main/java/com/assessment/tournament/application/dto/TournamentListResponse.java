package com.assessment.tournament.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class TournamentListResponse {
    private List<TournamentResponseDto> tournaments;
}
