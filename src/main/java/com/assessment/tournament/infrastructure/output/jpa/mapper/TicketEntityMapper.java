package com.assessment.tournament.infrastructure.output.jpa.mapper;

import com.assessment.tournament.domain.model.Ticket;
import com.assessment.tournament.infrastructure.output.jpa.entity.TicketEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface TicketEntityMapper {
    Ticket toModel(TicketEntity ticketEntity);
    TicketEntity toEntity(Ticket ticket);
}
