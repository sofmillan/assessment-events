package com.assessment.tournament.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketResponseDto {
    private String code;
    private LocalDateTime purchaseDate;
    private String tournamentName;
}
