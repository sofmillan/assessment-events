package com.assessment.tournament.application.mapper;

import com.assessment.tournament.application.dto.TicketResponseDto;
import com.assessment.tournament.domain.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TicketDtoMapper {
    @Mapping(target = "tournamentName", source = "ticket.tournament.name")
    TicketResponseDto toResponseDto(Ticket ticket);
}
