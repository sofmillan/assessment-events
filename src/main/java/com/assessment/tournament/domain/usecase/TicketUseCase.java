package com.assessment.tournament.domain.usecase;

import com.assessment.tournament.domain.api.TicketServicePort;
import com.assessment.tournament.domain.constants.Constants;
import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.spi.TicketPersistencePort;

public class TicketUseCase implements TicketServicePort {
    private final TicketPersistencePort ticketPersistencePort;

    public TicketUseCase(TicketPersistencePort ticketPersistencePort) {
        this.ticketPersistencePort = ticketPersistencePort;
    }

    @Override
    public Ticket save(Ticket ticket) {
        ticket.setTotalPrice(calculateTicketTotalPrice(ticket.getTournament().getTicketPrice()));
        return ticketPersistencePort.save(ticket);
    }

    private static Double calculateTicketTotalPrice(Double basePrice){
        return basePrice + (basePrice* Constants.PLATFORM_FEE);
    }
}
