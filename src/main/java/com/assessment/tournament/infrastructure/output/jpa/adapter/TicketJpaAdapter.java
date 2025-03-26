package com.assessment.tournament.infrastructure.output.jpa.adapter;

import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.spi.TicketPersistencePort;
import com.assessment.tournament.infrastructure.output.jpa.entity.TicketEntity;
import com.assessment.tournament.infrastructure.output.jpa.mapper.TicketEntityMapper;
import com.assessment.tournament.infrastructure.output.jpa.repository.TicketRepository;
import com.assessment.tournament.infrastructure.output.jpa.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketPersistencePort {

    private final TicketRepository ticketRepository;
    private final TicketEntityMapper ticketEntityMapper;

    @Override
    public Ticket save(Ticket ticket) {
        TicketEntity ticketEntity = ticketEntityMapper.toEntity(ticket);
        ticketEntity.setPurchaseDate(LocalDateTime.now());
        ticketEntity.setCode(UUID.randomUUID().toString().substring(0,8));
        ticketRepository.save(ticketEntity);
        ticketEntity.getTournament().setRemainingCapacity(ticketEntity.getTournament().getRemainingCapacity()-1);
        return ticketEntityMapper.toModel(ticketEntity);
    }
}
