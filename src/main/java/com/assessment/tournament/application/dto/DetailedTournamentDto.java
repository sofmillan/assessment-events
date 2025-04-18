package com.assessment.tournament.application.dto;

import lombok.Data;

import java.time.LocalDate;
@Data

public class DetailedTournamentDto {
    private Long id;
    private String description;
    private String name;
    private String category;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double ticketPrice;
    private boolean isFree;
    private Integer remainingCapacity;
    private Integer participants;

    public void setIsFree(boolean free) {
        isFree = free;
    }
}
