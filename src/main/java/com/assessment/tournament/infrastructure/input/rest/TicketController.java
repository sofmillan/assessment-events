package com.assessment.tournament.infrastructure.input.rest;

import com.assessment.tournament.application.dto.TicketRequestDto;
import com.assessment.tournament.application.dto.TicketResponseDto;
import com.assessment.tournament.application.handler.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class TicketController {
    private final TicketHandler ticketHandler;
    @PostMapping("/tickets")
    public TicketResponseDto save(@RequestBody TicketRequestDto ticketRequestDto, @RequestHeader(name = "Authorization") String token){
        return ticketHandler.save(ticketRequestDto, token);
    }
}
