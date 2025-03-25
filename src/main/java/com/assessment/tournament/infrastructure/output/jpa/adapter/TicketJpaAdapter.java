package com.assessment.tournament.infrastructure.output.jpa.adapter;

import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.spi.TicketPersistencePort;

public class TicketJpaAdapter implements TicketPersistencePort {
    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }
}
