package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentMapper {
    @Mapping(target = "commitment", expression = "java(toCommitment(paymentDto.getCommitmentId()))")
    Payment paymentDtoToPayment(PaymentDto paymentDto);

    @Mapping(target = "commitmentId", source = "commitment.id")
    PaymentDto paymentToPaymentDto(Payment payment);

    default Commitment toCommitment(Long id) {
        if (id == null) return null;
        Commitment c = new Commitment();
        c.setId(id);
        return c;
    }
}
