package com.assessment.tournament.application.dto;

import lombok.Data;

import java.time.LocalDate;
@Data

public class TournamentResponseDto {
    private Long id;
    private String name;
    private Integer remainingCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double ticketPrice;
    private Boolean isFree;

}
