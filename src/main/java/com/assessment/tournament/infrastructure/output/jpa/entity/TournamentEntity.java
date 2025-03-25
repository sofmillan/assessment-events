package com.assessment.tournament.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "tournament")
@Data
public class TournamentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "start_date", columnDefinition = "DATE")
    private LocalDate startDate;


    @Column(name = "end_date", columnDefinition = "DATE")
    private LocalDate endDate;
    private String userId;
    private Double ticketPrice;
    private Integer remainingCapacity;

    @Column(name = "free", columnDefinition = "BOOLEAN")
    private Boolean free;

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
