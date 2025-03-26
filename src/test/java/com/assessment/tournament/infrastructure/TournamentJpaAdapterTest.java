package com.assessment.tournament.infrastructure;

import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.infrastructure.exception.DataNotFoundException;
import com.assessment.tournament.infrastructure.output.jpa.adapter.TournamentJpaAdapter;
import com.assessment.tournament.infrastructure.output.jpa.entity.CategoryEntity;
import com.assessment.tournament.infrastructure.output.jpa.entity.TournamentEntity;
import com.assessment.tournament.infrastructure.output.jpa.mapper.TournamentEntityMapper;
import com.assessment.tournament.infrastructure.output.jpa.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TournamentJpaAdapterTest {
    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TournamentEntityMapper tournamentEntityMapper;

    @InjectMocks
    private TournamentJpaAdapter tournamentJpaAdapter;

    @Test
    void should_throwDataNotFoundException_when_tournamentNotFound(){
        //Arrange
        Long nonExistentTournamentId = 5L;
        when(tournamentRepository.findById(nonExistentTournamentId)).thenReturn(Optional.empty());

        //Act and Assert
        assertThrows(DataNotFoundException.class, ()-> tournamentJpaAdapter.getById(nonExistentTournamentId));
    }

//    @Test
//    void should_findTournamentById(){
//        //Arrange
//        Long tournamentId = 1L;
//        Category category = new Category(1L, "Gold",10);
//        CategoryEntity categoryEntity = new CategoryEntity(1L, "Gold",10);
///*
//        Tournament expectedTournament = new Tournament(1L, "Sample description", "Sample name",category, LocalDate.of(2025,10,10),LocalDate.of(2025,10,20),0.0, true);
//        TournamentEntity tournamentEntity = new TournamentEntity(1L, "Sample description", "Sample name",categoryEntity, LocalDate.of(2025,10,10),LocalDate.of(2025,10,20),0.0, true);
//*/
//    }
}
