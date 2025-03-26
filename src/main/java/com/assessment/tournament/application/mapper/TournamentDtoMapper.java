package com.assessment.tournament.application.mapper;

import com.assessment.tournament.application.dto.DetailedTournamentDto;
import com.assessment.tournament.application.dto.TournamentRequestDto;
import com.assessment.tournament.application.dto.TournamentResponseDto;
import com.assessment.tournament.domain.model.Tournament;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TournamentDtoMapper {
    Tournament toModel(TournamentRequestDto tournamentRequestDto);
    TournamentResponseDto toResponseDto(Tournament tournament);
    @Mapping(target = "category", source = "tournament.category.name")
    @Mapping(target = "participants", source = "tournament", qualifiedByName = "getNumberOfParticipants")
    DetailedTournamentDto toDetailedResponseDto(Tournament tournament);

    @Named("getNumberOfParticipants")
    default int getNumberOfParticipants(Tournament tournament) {
        return tournament.getCategory().getCapacity() - tournament.getRemainingCapacity();
    }
}
