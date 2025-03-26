package com.assessment.tournament.domain.usecase;

import com.assessment.tournament.domain.exception.FreeTournamentLimitException;
import com.assessment.tournament.domain.api.TournamentServicePort;
import com.assessment.tournament.domain.model.Tournament;
import com.assessment.tournament.domain.spi.TournamentPersistencePort;

public class TournamentUseCase implements TournamentServicePort {

    private final TournamentPersistencePort tournamentPersistencePort;


    public TournamentUseCase(TournamentPersistencePort tournamentPersistencePort) {
        this.tournamentPersistencePort = tournamentPersistencePort;
    }

    @Override
    public Tournament save(Tournament tournament) {
        if(tournament.getIsFree() &&
                tournamentPersistencePort.getByUserId(tournament.getUserId()).stream().filter(Tournament::getIsFree).count()>=2){
            throw new FreeTournamentLimitException("User cannot create more free tournaments");
        }

        tournament.setRemainingCapacity(tournament.getCategory().getCapacity());
        return tournamentPersistencePort.save(tournament);
    }

    @Override
    public Tournament findById(Long id) {
        return tournamentPersistencePort.getById(id);
    }

    @Override
    public Tournament updateRemainingCapacity(Tournament tournament) {
        tournament.setRemainingCapacity(tournament.getRemainingCapacity()-1);
        return tournamentPersistencePort.save(tournament);
    }
}
