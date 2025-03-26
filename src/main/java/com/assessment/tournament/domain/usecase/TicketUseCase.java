package com.assessment.tournament.domain.usecase;

import com.assessment.tournament.domain.api.TicketServicePort;
import com.assessment.tournament.domain.constants.Constants;
import com.assessment.tournament.domain.exception.TournamentSoldOutException;
import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.domain.spi.TicketPersistencePort;

public class TicketUseCase implements TicketServicePort {
    private final TicketPersistencePort ticketPersistencePort;


    public TicketUseCase(TicketPersistencePort ticketPersistencePort) {
        this.ticketPersistencePort = ticketPersistencePort;

    }

    @Override
    public Ticket save(Ticket ticket) {
        Tournament tournament = ticket.getTournament();
        if(tournament.getRemainingCapacity()==0){
            throw new TournamentSoldOutException("This tournament has no available capacity");
        }
        ticket.setTotalPrice(calculateTicketTotalPrice(ticket.getTournament().getTicketPrice()));
        Ticket savedTicket = ticketPersistencePort.save(ticket);

        return savedTicket;
    }

    private static Double calculateTicketTotalPrice(Double basePrice){
        return basePrice + (basePrice* Constants.PLATFORM_FEE);
    }
}
