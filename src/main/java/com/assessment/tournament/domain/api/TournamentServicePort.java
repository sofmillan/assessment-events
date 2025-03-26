package com.assessment.tournament.domain.api;

import com.assessment.tournament.domain.model.Tournament;

import java.util.List;

public interface TournamentServicePort {
    Tournament save(Tournament tournament);
    Tournament findById(Long id);
    Tournament updateRemainingCapacity(Tournament tournament);
    List<Tournament> findByUserId(String userId);
}
