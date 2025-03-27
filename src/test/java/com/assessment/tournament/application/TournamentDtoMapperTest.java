package com.assessment.tournament.application;

import com.assessment.tournament.application.mapper.TournamentDtoMapper;
import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.model.Tournament;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TournamentDtoMapperTest {

    private final TournamentDtoMapper mapper = Mappers.getMapper(TournamentDtoMapper.class);

    @Test
    void getNumberOfParticipants_ShouldReturnCorrectValue() {
        // Arrange
        Category category = new Category();
        category.setCapacity(100);

        Tournament tournament = new Tournament();
        tournament.setCategory(category);
        tournament.setRemainingCapacity(40);

        // Act
        int participants = mapper.getNumberOfParticipants(tournament);

        // Assert
        assertEquals(60, participants);
    }
}
