package com.assessment.tournament.domain.spi;

import com.assessment.tournament.domain.model.Tournament;

import java.util.List;

public interface TournamentPersistencePort {
    Tournament save(Tournament tournament);
    List<Tournament> getByUserId(String userId);
    List<Tournament> getAll();
    Tournament getById(Long id);
}
