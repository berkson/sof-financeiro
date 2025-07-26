package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.model.CommitmentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {PaymentMapper.class})
public interface CommitmentMapper {
    //CommitmentMapper INSTANCE = Mappers.getMapper(CommitmentMapper.class);

    @Mapping(target = "expense", ignore = true)
    Commitment commitmentDtoToCommitment(CommitmentDto commitmentDto);

    @Mapping(target = "expense", ignore = true)
    CommitmentDto commitmentToCommitmentDto(Commitment commitment);
}
