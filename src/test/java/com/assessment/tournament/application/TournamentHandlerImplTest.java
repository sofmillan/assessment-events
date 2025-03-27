package com.assessment.tournament.application;

import com.assessment.tournament.application.dto.DetailedTournamentDto;
import com.assessment.tournament.application.dto.TournamentListResponse;
import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;
import com.assessment.tournament.application.handler.TournamentHandlerImpl;
import com.assessment.tournament.application.mapper.TournamentDtoMapper;
import com.assessment.tournament.domain.api.CategoryServicePort;
import com.assessment.tournament.domain.api.IdentityResolver;
import com.assessment.tournament.domain.api.TournamentServicePort;
import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.model.Tournament;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TournamentHandlerImplTest {
    @Mock
    private CategoryServicePort categoryServicePort;
    @Mock
    private TournamentServicePort tournamentServicePort;
    @Mock
    private TournamentDtoMapper tournamentDtoMapper;
    @Mock
    private IdentityResolver identityResolver;

    @InjectMocks
    private TournamentHandlerImpl tournamentHandler;
    private String token;
    private String userId;
    private Tournament tournament;
    private Category category;

    @BeforeEach
    void setUp() {
        token = "eyJhbGciOiJIUzIXVCJ9.eyJzdWIiOiIxMjM0NDkwIiwibmFtZSkpvaoxN.SflKxwRJKF2QT4fwpMeJf";
        userId = "b6f8a4c0-5d7f-4e92-8a8e-3a9b2d4c6f1e";

        category = new Category();
        category.setId(1L);
        category.setName("Gold");
        category.setCapacity(20);

        tournament = new Tournament();
        tournament.setUserId(userId);
        tournament.setIsFree(true);
        tournament.setCategory(category);
        tournament.setName("LVLY");
        tournament.setRemainingCapacity(20);
    }

    @Test
    void shouldSaveTournamentWhenValidRequest() {
        // Arrange
        Long categoryId = 1L;
        TournamentRequestDto requestDto = new TournamentRequestDto();
        requestDto.setCategoryId(categoryId);

        Tournament savedTournament = new Tournament();
        savedTournament.setId(1L);

        TournamentResponseDto responseDto = new TournamentResponseDto();
        responseDto.setId(1L);

        when(identityResolver.getUserIdFromToken(token)).thenReturn(userId);
        when(categoryServicePort.findById(categoryId)).thenReturn(category);
        when(tournamentDtoMapper.toModel(requestDto)).thenReturn(tournament);
        when(tournamentServicePort.save(tournament)).thenReturn(savedTournament);
        when(tournamentDtoMapper.toResponseDto(savedTournament)).thenReturn(responseDto);

        // Act
        TournamentResponseDto result = tournamentHandler.saveTournament(requestDto, token);

        // Assert
        assertEquals(savedTournament.getId(), result.getId());
        verify(identityResolver).getUserIdFromToken(token);
        verify(categoryServicePort).findById(categoryId);
        verify(tournamentServicePort).save(tournament);
        verify(tournamentDtoMapper).toResponseDto(savedTournament);
    }

    @Test
    void shouldReturnUserTournamentsWhenCreatedByMeIsTrue() {
        // Arrange
        List<Tournament> tournaments = List.of(tournament);
        List<TournamentResponseDto> tournamentDtos = List.of(new TournamentResponseDto());

        when(identityResolver.getUserIdFromToken(token)).thenReturn(userId);
        when(tournamentServicePort.findByUserId(userId)).thenReturn(tournaments);
        when(tournamentDtoMapper.toResponseDto(any(Tournament.class))).thenReturn(tournamentDtos.get(0));

        // Act
        TournamentListResponse result = tournamentHandler.getTournamentsByUserId(true, token);

        // Assert
        assertEquals(1, result.getTournaments().size());
        verify(identityResolver).getUserIdFromToken(token);
        verify(tournamentServicePort).findByUserId(userId);
    }

    @Test
    void shouldReturnAllTournamentsWhenCreatedByMeIsFalse() {
        // Arrange
        List<Tournament> tournaments = List.of(tournament);
        List<TournamentResponseDto> tournamentDtos = List.of(new TournamentResponseDto());

        when(tournamentServicePort.getAll()).thenReturn(tournaments);
        when(tournamentDtoMapper.toResponseDto(any(Tournament.class))).thenReturn(tournamentDtos.get(0));

        // Act
        TournamentListResponse result = tournamentHandler.getTournamentsByUserId(false, token);

        // Assert
        assertEquals(1, result.getTournaments().size());
        verify(tournamentServicePort).getAll();
    }

    @Test
    void ShouldReturnTournamentDetailsWhenGetTournamentById() {
        // Arrange
        Long tournamentId = 1L;

        DetailedTournamentDto detailedTournamentDto = new DetailedTournamentDto();
        detailedTournamentDto.setId(tournamentId);

        when(tournamentServicePort.findById(tournamentId)).thenReturn(tournament);
        when(tournamentDtoMapper.toDetailedResponseDto(tournament)).thenReturn(detailedTournamentDto);

        // Act
        DetailedTournamentDto result = tournamentHandler.getTournamentById(tournamentId);

        // Assert
        assertEquals(tournamentId, result.getId());
        verify(tournamentServicePort).findById(tournamentId);
        verify(tournamentDtoMapper).toDetailedResponseDto(tournament);
    }
}