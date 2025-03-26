package com.assessment.tournament.infrastructure.jpa;

import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.infrastructure.exception.DataNotFoundException;
import com.assessment.tournament.infrastructure.output.jpa.adapter.TournamentJpaAdapter;
import com.assessment.tournament.infrastructure.output.jpa.entity.TournamentEntity;
import com.assessment.tournament.infrastructure.output.jpa.mapper.TournamentEntityMapper;
import com.assessment.tournament.infrastructure.output.jpa.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentJpaAdapterTest {
    @Mock
    private TournamentRepository tournamentRepository;
    @Mock
    private TournamentEntityMapper tournamentEntityMapper;
    @InjectMocks
    private TournamentJpaAdapter tournamentJpaAdapter;
    private TournamentEntity tournamentEntity;
    private Tournament tournament;
    private String userId ;


    @BeforeEach
    void setUp() {
        userId = "e3f14d8a-9c4b-4a6d-b1f2-7b3e8a6f9d27";
        tournamentEntity = new TournamentEntity();
        tournamentEntity.setId(1L);
        tournamentEntity.setName("Solar Power");
        tournamentEntity.setUserId(userId);

        tournament = new Tournament();
        tournament.setId(1L);
        tournament.setName("Solar Power");
        tournament.setUserId(userId);
    }

    @Test
    void shouldThrowDataNotFoundException_whenTournamentNotFound(){
        //Arrange
        Long nonExistentTournamentId = 5L;
        when(tournamentRepository.findById(nonExistentTournamentId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(DataNotFoundException.class, ()-> tournamentJpaAdapter.getById(nonExistentTournamentId));
        verify(tournamentRepository).findById(nonExistentTournamentId);
    }

    @Test
    void shouldReturnTournament_whenTournamentExists(){
        //Arrange
        Long existentTournamentId = 1L;

        when(tournamentRepository.findById(existentTournamentId)).thenReturn(Optional.of(tournamentEntity));
        when(tournamentEntityMapper.toModel(tournamentEntity)).thenReturn(tournament);

        //Act
        Tournament foundTournament = tournamentJpaAdapter.getById(existentTournamentId);

        //Assert
        assertNotNull(foundTournament);
        assertEquals(tournament.getId(), foundTournament.getId());
        assertEquals(tournament.getName(), foundTournament.getName());
        verify(tournamentRepository).findById(existentTournamentId);
        verify(tournamentEntityMapper).toModel(tournamentEntity);
    }

    @Test
    void shouldReturnListOfTournaments_whenTournamentsExist() {
        // Arrange
        TournamentEntity tournamentEntity2 = new TournamentEntity();
        tournamentEntity2.setId(2L);
        tournamentEntity2.setName("GRL GVNG");

        Tournament tournament2 = new Tournament();
        tournament2.setId(2L);
        tournament2.setName("GRL GVNG");

        when(tournamentRepository.findAll()).thenReturn(List.of(tournamentEntity, tournamentEntity2));
        when(tournamentEntityMapper.toModel(tournamentEntity)).thenReturn(tournament);
        when(tournamentEntityMapper.toModel(tournamentEntity2)).thenReturn(tournament2);

        // Act
        List<Tournament> tournaments = tournamentJpaAdapter.getAll();

        // Assert
        assertNotNull(tournaments);
        assertEquals(2, tournaments.size());
        assertEquals(1L, tournaments.get(0).getId());
        assertEquals(2L, tournaments.get(1).getId());
        assertEquals("Solar Power", tournaments.get(0).getName());
        assertEquals("GRL GVNG", tournaments.get(1).getName());
        verify(tournamentRepository).findAll();
        verify(tournamentEntityMapper, times(2)).toModel(any(TournamentEntity.class));
    }

    @Test
    void shouldReturnTournament_whenTournamentIsSavedSuccessfully() {
        // Arrange
        when(tournamentEntityMapper.toEntity(tournament)).thenReturn(tournamentEntity);
        when(tournamentRepository.save(tournamentEntity)).thenReturn(tournamentEntity);
        when(tournamentEntityMapper.toModel(tournamentEntity)).thenReturn(tournament);

        // Act
        Tournament savedTournament = tournamentJpaAdapter.save(tournament);

        // Assert
        assertNotNull(savedTournament);
        assertEquals(tournament.getId(), savedTournament.getId());
        assertEquals(tournament.getName(), savedTournament.getName());
        assertEquals(tournament.getStartDate(), savedTournament.getStartDate());
        verify(tournamentEntityMapper).toEntity(tournament);
        verify(tournamentEntityMapper).toModel(tournamentEntity);
        verify(tournamentRepository).save(any(TournamentEntity.class));
    }

    @Test
    void shouldReturnListOfTournaments_whenUserHasTournaments() {
        // Arrange
        TournamentEntity tournamentEntity2 = new TournamentEntity();
        tournamentEntity2.setId(2L);
        tournamentEntity2.setName("GRL GVNG");
        tournamentEntity2.setUserId(userId);

        Tournament tournament2 = new Tournament();
        tournament2.setId(2L);
        tournament2.setName("GRL GVNG");
        tournament2.setUserId(userId);

        when(tournamentRepository.findByUserId(userId)).thenReturn(List.of(tournamentEntity, tournamentEntity2));
        when(tournamentEntityMapper.toModel(tournamentEntity)).thenReturn(tournament);
        when(tournamentEntityMapper.toModel(tournamentEntity2)).thenReturn(tournament2);

        // Act
        List<Tournament> tournaments = tournamentJpaAdapter.getByUserId(userId);

        // Assert
        assertNotNull(tournaments);
        assertEquals(2, tournaments.size());
        assertEquals(1L, tournaments.get(0).getId());
        assertEquals(2L, tournaments.get(1).getId());
        assertEquals("Solar Power", tournaments.get(0).getName());
        assertEquals("GRL GVNG", tournaments.get(1).getName());
        assertEquals(userId, tournaments.get(0).getUserId());
        assertEquals(userId, tournaments.get(1).getUserId());
        verify(tournamentRepository).findByUserId(userId);
        verify(tournamentEntityMapper, times(2)).toModel(any(TournamentEntity.class));
    }

}
