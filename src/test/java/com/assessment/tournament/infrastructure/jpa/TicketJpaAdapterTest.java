package com.assessment.tournament.infrastructure.jpa;

import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.infrastructure.output.jpa.adapter.TicketJpaAdapter;
import com.assessment.tournament.infrastructure.output.jpa.entity.TicketEntity;
import com.assessment.tournament.infrastructure.output.jpa.mapper.TicketEntityMapper;
import com.assessment.tournament.infrastructure.output.jpa.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketJpaAdapterTest {
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketEntityMapper ticketEntityMapper;
    @InjectMocks
    private TicketJpaAdapter ticketJpaAdapter;


    @Test
    void shouldSaveTicketAndReturnSavedTicket() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(1L);

        Ticket expectedTicket = new Ticket();
        expectedTicket.setId(1L);
        expectedTicket.setPurchaseDate(LocalDateTime.of(2025, 3, 26, 12, 0));
        expectedTicket.setCode("a1b2c3d4");

        when(ticketEntityMapper.toEntity(ticket)).thenReturn(ticketEntity);
        when(ticketRepository.save(any(TicketEntity.class))).thenReturn(ticketEntity);
        when(ticketEntityMapper.toModel(ticketEntity)).thenReturn(expectedTicket);

        // Act
        Ticket result = ticketJpaAdapter.save(ticket);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTicket.getId(), result.getId());
        assertEquals(expectedTicket.getCode(), result.getCode());
        assertEquals(expectedTicket.getPurchaseDate(), result.getPurchaseDate());
        verify(ticketEntityMapper).toEntity(ticket);
        verify(ticketRepository).save(any(TicketEntity.class));
    }
}
