package com.assessment.tournament.application.handler;

import com.assessment.tournament.application.dto.DetailedTournamentDto;
import com.assessment.tournament.application.dto.TournamentListResponse;
import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;
import com.assessment.tournament.application.mapper.TournamentDtoMapper;
import com.assessment.tournament.domain.api.CategoryServicePort;
import com.assessment.tournament.domain.api.IdentityResolver;
import com.assessment.tournament.domain.api.TournamentServicePort;
import com.assessment.tournament.domain.model.Category;
import com.assessment.tournament.domain.model.Tournament;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TournamentHandlerImpl implements TournamentHandler{
    private final CategoryServicePort categoryServicePort;
    private final TournamentServicePort tournamentServicePort;
    private final TournamentDtoMapper tournamentDtoMapper;
    private final IdentityResolver identityResolver;
    @Override
    public TournamentResponseDto saveTournament(TournamentRequestDto tournamentRequestDto, String token) {
        String userId = identityResolver.getUserIdFromToken(token);
        Category category = categoryServicePort.findById(tournamentRequestDto.getCategoryId());
        Tournament tournament = tournamentDtoMapper.toModel(tournamentRequestDto);
        tournament.setCategory(category);
        tournament.setUserId(userId);
        return tournamentDtoMapper.toResponseDto(tournamentServicePort.save(tournament));
    }

    @Override
    public TournamentListResponse getTournamentsByUserId(boolean createdByMe, String token) {
        List<TournamentResponseDto> tournaments = createdByMe
                ? getUserTournaments(token)
                : getAllTournaments();

        return new TournamentListResponse(tournaments);
    }

    @Override
    public DetailedTournamentDto getTournamentById(Long id) {
        return tournamentDtoMapper.toDetailedResponseDto(tournamentServicePort.findById(id));
    }

    private List<TournamentResponseDto> getUserTournaments(String token) {
        String userId = identityResolver.getUserIdFromToken(token);
        return tournamentServicePort.findByUserId(userId)
                .stream()
                .map(tournamentDtoMapper::toResponseDto)
                .toList();
    }

    private List<TournamentResponseDto> getAllTournaments() {
        return tournamentServicePort.getAll()
                .stream()
                .map(tournamentDtoMapper::toResponseDto)
                .toList();
    }
}
