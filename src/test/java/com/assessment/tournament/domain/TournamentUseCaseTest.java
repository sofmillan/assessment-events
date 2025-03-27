package com.assessment.tournament.domain;

import com.assessment.tournament.domain.exception.FreeTournamentLimitException;
import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.domain.spi.TournamentPersistencePort;
import com.assessment.tournament.domain.usecase.TournamentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentUseCaseTest {

    @Mock
    private TournamentPersistencePort tournamentPersistencePort;

    @InjectMocks
    private TournamentUseCase tournamentUseCase;
    private Tournament tournament;
    private String userId;
    private Category category;

    @BeforeEach
    void setUp(){
        userId= "f47ac10b-58cc-4372-a567-0e02b2c3d479";
        category = new Category(1L, "Gold", 20);

        tournament = new Tournament();
        tournament.setUserId(userId);
        tournament.setIsFree(true);
        tournament.setCategory(category);
        tournament.setName("LVLY");
        tournament.setRemainingCapacity(20);
        tournament.setDescription("This is a generic description");
        tournament.setStartDate(LocalDate.of(2025,10,10));
        tournament.setEndDate(LocalDate.of(2025,12,10));
    }

    @Test
    void shouldSaveTournamentWhenUserHasLessThanTwoFreeTournaments() {
        // Arrange
        when(tournamentPersistencePort.getByUserId(userId)).thenReturn(List.of());
        when(tournamentPersistencePort.save(any(Tournament.class))).thenReturn(tournament);

        // Act
        Tournament result = tournamentUseCase.save(tournament);

        // Assert
        assertNotNull(result);
        assertEquals(20, result.getRemainingCapacity());
        assertEquals(tournament.getName(), result.getName());
        assertEquals(tournament.getDescription(), result.getDescription());
        assertEquals(tournament.getStartDate(), result.getStartDate());
        assertEquals(tournament.getEndDate(), result.getEndDate());
        assertEquals(tournament.getTicketPrice(), result.getTicketPrice());
        assertEquals(tournament.getIsFree(), result.getIsFree());
        verify(tournamentPersistencePort).save(any(Tournament.class));
    }

    @Test
    void shouldThrowExceptionWhenUserHasTwoFreeTournaments() {
        // Arrange
        Tournament existingFreeTournament = new Tournament();
        existingFreeTournament.setName("GRL GVNG");
        existingFreeTournament.setUserId(userId);
        existingFreeTournament.setIsFree(true);
        existingFreeTournament.setCategory(category);

        List<Tournament> existingFreeTournaments = List.of(existingFreeTournament, existingFreeTournament);

        when(tournamentPersistencePort.getByUserId(userId)).thenReturn(existingFreeTournaments);

        // Act & Assert
        assertThrows(FreeTournamentLimitException.class, () -> tournamentUseCase.save(tournament));
        verify(tournamentPersistencePort, never()).save(any(Tournament.class));
    }

    @Test
    void shouldSavePaidTournamentWhenUserHasTwoFreeTournaments() {
        // Arrange

        tournament.setIsFree(false);
        tournament.setTicketPrice(1.00);

        when(tournamentPersistencePort.save(any(Tournament.class))).thenReturn(tournament);
        //Act
        Tournament result = tournamentUseCase.save(tournament);
        // Assert
        assertNotNull(result);
        assertEquals(tournament.getIsFree(), result.getIsFree());
        verify(tournamentPersistencePort).save(any(Tournament.class));
    }

    @Test
    void shouldFindTournamentByIdWhenTournamentExists() {
        // Arrange
        Long tournamentId = 1L;
        Tournament existingTournament = new Tournament();
        existingTournament.setId(tournamentId);

        when(tournamentPersistencePort.getById(tournamentId)).thenReturn(existingTournament);

        // Act
        Tournament result = tournamentUseCase.findById(tournamentId);

        // Assert
        assertNotNull(result);
        assertEquals(tournamentId, result.getId());
        verify(tournamentPersistencePort).getById(tournamentId);
    }

    @Test
    void shouldUpdateRemainingCapacity() {
        // Arrange
        Tournament updatedTournament = new Tournament();
        updatedTournament.setRemainingCapacity(19);

        when(tournamentPersistencePort.save(any(Tournament.class))).thenReturn(updatedTournament);

        // Act
        Tournament result = tournamentUseCase.updateRemainingCapacity(tournament);

        // Assert
        assertEquals(19, result.getRemainingCapacity());
        verify(tournamentPersistencePort).save(any(Tournament.class));
    }

    @Test
    void shouldReturnTournamentsByUserId() {
        // Arrange
        List<Tournament> tournaments = List.of(new Tournament(), new Tournament());

        when(tournamentPersistencePort.getByUserId(userId)).thenReturn(tournaments);

        // Act
        List<Tournament> result = tournamentUseCase.findByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        verify(tournamentPersistencePort).getByUserId(userId);
    }

    @Test
    void shouldReturnAllTournaments() {
        // Arrange
        List<Tournament> tournaments = List.of(new Tournament(), new Tournament(), new Tournament());

        when(tournamentPersistencePort.getAll()).thenReturn(tournaments);

        // Act
        List<Tournament> result = tournamentUseCase.getAll();

        // Assert
        assertEquals(3, result.size());
        verify(tournamentPersistencePort).getAll();
    }
}
