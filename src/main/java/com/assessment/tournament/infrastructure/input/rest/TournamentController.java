package com.assessment.tournament.infrastructure.input.rest;

import com.assessment.tournament.application.dto.DetailedTournamentDto;
import com.assessment.tournament.application.dto.TournamentListResponse;
import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;
import com.assessment.tournament.application.handler.TournamentHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentHandler tournamentHandler;

    @PostMapping("/tournaments")
    public TournamentResponseDto save(@Valid @RequestBody TournamentRequestDto tournamentRequestDto, @RequestHeader(name = "Authorization") String token){
       return tournamentHandler.saveTournament(tournamentRequestDto, token);
    }

    @GetMapping("/tournaments")
    public TournamentListResponse getTournamentsByUser(@RequestParam(required = false, defaultValue = "false") boolean createdByMe, @RequestHeader(name = "Authorization") String token){
        return tournamentHandler.getTournamentsByUserId(createdByMe, token);
    }

    @GetMapping("/tournaments/{idTournament}")
    public DetailedTournamentDto getTournamentById(@PathVariable Long idTournament){
        return tournamentHandler.getTournamentById(idTournament);
    }
}
