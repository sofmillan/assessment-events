package com.assessment.tournament.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
public class TicketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private TournamentEntity tournament;
    private Double totalPrice;
    private LocalDateTime purchaseDate;
    private String code;
}
