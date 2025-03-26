package com.assessment.tournament.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TournamentRequestDto {
    @NotNull(message = "Name is required")
    @Size(min = 1, max=50, message = "Name must be between 1 and 50 characters")
    private String name;

    @Size(min = 1, max=100, message = "Description must be between 1 and 100 characters")
    private String description;

    @NotNull(message = "Category id is required")
    private Long categoryId;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotNull(message = "Ticket price is required")
    private Double ticketPrice;

    @NotNull(message = "IsFree is required")
    private Boolean isFree;

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }
}
