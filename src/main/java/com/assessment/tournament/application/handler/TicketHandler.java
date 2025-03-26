package com.assessment.tournament.application.handler;

import com.assessment.tournament.application.dto.TicketRequestDto;
import com.assessment.tournament.application.dto.TicketResponseDto;

public interface TicketHandler {
    TicketResponseDto save(TicketRequestDto ticketRequestDto, String token);
}
