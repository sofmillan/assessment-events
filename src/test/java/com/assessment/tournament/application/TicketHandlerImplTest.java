package com.assessment.tournament.application;

import com.assessment.tournament.application.dto.TicketRequestDto;
import com.assessment.tournament.application.dto.TicketResponseDto;
import com.assessment.tournament.application.handler.TicketHandlerImpl;
import com.assessment.tournament.application.mapper.TicketDtoMapper;
import com.assessment.tournament.domain.api.IdentityResolver;
import com.assessment.tournament.domain.api.TicketServicePort;
import com.assessment.tournament.domain.api.TournamentServicePort;
import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.domain.model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketHandlerImplTest {

    @Mock
    private TournamentServicePort tournamentServicePort;

    @Mock
    private TicketServicePort ticketServicePort;

    @Mock
    private IdentityResolver identityResolver;

    @Mock
    private TicketDtoMapper ticketDtoMapper;

    @InjectMocks
    private TicketHandlerImpl ticketHandler;
    private String token;
    private String userId;
    private Long tournamentId = 1L;
    private Tournament tournament;

    @BeforeEach
    void setUp(){
        token = "eyJhbGciOiJIUzIXVCJ9.eyJzdWIiOiIxMjM0NDkwIiwibmFtZSkpvaoxN.SflKxwRJKF2QT4fwpMeJf";
        userId = "b6f8a4c0-5d7f-4e92-8a8e-3a9b2d4c6f1e";
        tournamentId = 1L;

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setRemainingCapacity(10);
        tournament.setName("Twilight");
    }

    @Test
    void save_ShouldReturnTicketResponseDto_WhenValidRequest() {
        // Arrange
        TicketRequestDto requestDto = new TicketRequestDto();
        requestDto.setTournamentId(tournamentId);

        Ticket savedTicket = new Ticket();
        savedTicket.setUserId(userId);
        savedTicket.setTournament(tournament);

        TicketResponseDto responseDto = new TicketResponseDto();
        responseDto.setTournamentName(tournament.getName());
        responseDto.setCode("1ac3dab3");
        responseDto.setPurchaseDate(LocalDateTime.now());

        when(identityResolver.getUserIdFromToken(token)).thenReturn(userId);
        when(tournamentServicePort.findById(tournamentId)).thenReturn(tournament);
        when(ticketServicePort.save(any(Ticket.class))).thenReturn(savedTicket);
        when(ticketDtoMapper.toResponseDto(savedTicket)).thenReturn(responseDto);

        // Act
        TicketResponseDto result = ticketHandler.save(requestDto, token);

        // Assert
        assertNotNull(result);
        assertEquals(responseDto.getTournamentName(), result.getTournamentName());
        assertNotNull(result.getCode());
        assertNotNull(result.getPurchaseDate());

        verify(identityResolver).getUserIdFromToken(token);
        verify(tournamentServicePort).findById(tournamentId);
        verify(ticketServicePort).save(any(Ticket.class));
        verify(tournamentServicePort).updateRemainingCapacity(tournament);
        verify(ticketDtoMapper).toResponseDto(savedTicket);
    }
}