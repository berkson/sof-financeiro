package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {PaymentMapper.class, ExpenseMapper.class})
public interface CommitmentMapper {
    @Mapping(target = "expense.commitments", ignore = true)
    Commitment commitmentDtoToCommitment(CommitmentDto commitmentDto);

    @Mapping(target = "expense.commitments", ignore = true)
    CommitmentDto commitmentToCommitmentDto(Commitment commitment);
}
