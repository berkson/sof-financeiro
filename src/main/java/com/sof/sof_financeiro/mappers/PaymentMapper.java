package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.model.PaymentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PaymentMapper {
    //PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "commitment", ignore = true)
    Payment paymentDtoToPayment(PaymentDto paymentDto);

    @Mapping(target = "commitment", ignore = true)
    PaymentDto paymentToPaymentDto(Payment payment);
}
