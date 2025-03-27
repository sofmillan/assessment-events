package com.assessment.tournament.domain;

import com.assessment.tournament.domain.exception.TournamentSoldOutException;
import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.domain.spi.TicketPersistencePort;
import com.assessment.tournament.domain.usecase.TicketUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketUseCaseTest {

    @Mock
    private TicketPersistencePort ticketPersistencePort;

    @InjectMocks
    private TicketUseCase ticketUseCase;

    @Test
    void shouldSaveTicketWhenPaidTournamentHasCapacity() {
        // Arrange
        Tournament tournament = new Tournament();
        tournament.setRemainingCapacity(2);
        tournament.setTicketPrice(100.0);

        Ticket ticket = new Ticket();
        ticket.setTournament(tournament);
        ticket.setTotalPrice(105.0);
        ticket.setUserId("1f4d8c64-9eab-4a6e-bd9b-9f2a7e3c1d7e");

        when(ticketPersistencePort.save(ticket)).thenReturn(ticket);

        // Act
        Ticket result = ticketUseCase.save(ticket);

        // Assert
        assertNotNull(result);
        assertEquals(105.0, result.getTotalPrice());
        assertEquals(ticket.getUserId(), ticket.getUserId());
        verify(ticketPersistencePort).save(any(Ticket.class));
    }

    @Test
    void shouldSaveTicketWhenFreeTournamentHasCapacity() {
        // Arrange
        Tournament tournament = new Tournament();
        tournament.setRemainingCapacity(2);
        tournament.setTicketPrice(0.0);
        tournament.setIsFree(true);

        Ticket ticket = new Ticket();
        ticket.setTournament(tournament);
        ticket.setTotalPrice(0.0);

        when(ticketPersistencePort.save(ticket)).thenReturn(ticket);

        // Act
        Ticket result = ticketUseCase.save(ticket);

        // Assert
        assertNotNull(result);
        assertEquals(0.0, result.getTotalPrice());
        verify(ticketPersistencePort).save(any(Ticket.class));
    }

    @Test
    void shouldThrowExceptionWhenTournamentIsSoldOut() {
        // Arrange
        Tournament tournament = new Tournament();
        tournament.setRemainingCapacity(0);

        Ticket ticket = new Ticket();
        ticket.setTournament(tournament);

        // Act and Assert
        assertThrows(TournamentSoldOutException.class, ()->ticketUseCase.save(ticket));
        verify(ticketPersistencePort, never()).save(any(Ticket.class));
    }
}
