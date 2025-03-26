package com.assessment.tournament.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequestDto {
    @NotNull(message = "Tournament id is required")
    private Long tournamentId;
}
