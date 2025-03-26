package com.assessment.tournament.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDto {
    @NotNull(message = "tournamentId is required")
    private Long tournamentId;
}
