package com.assessment.tournament.infrastructure.input.rest;

import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.handler.TournamentHandler;
import com.assessment.tournament.infrastructure.output.jpa.entity.CategoryEntity;
import com.assessment.tournament.infrastructure.output.jpa.repository.CategoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentHandler tournamentHandler;
    private final CategoryRepository categoryRepository;

    @PostMapping("/tournaments")
    public void save(@RequestBody TournamentRequestDto tournamentRequestDto, @RequestHeader(name = "Authorization") String token){
        tournamentHandler.saveTournament(tournamentRequestDto, token);
    }

    @PostMapping
    public void category(@RequestBody CategoryEntity category){
        categoryRepository.save(category);
    }




}
