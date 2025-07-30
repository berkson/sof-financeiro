package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import com.sof.sof_financeiro.repository.PaymentRepository;
import com.sof.sof_financeiro.services.CommitmentService;
import com.sof.sof_financeiro.validations.annotations.CheckPaymentValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created By : Berkson Ximenes
 * Date : 29/07/2025
 **/
@Component
public class CheckPaymentValueValidator implements ConstraintValidator<CheckPaymentValue, PaymentDto> {

    private final CommitmentService commitmentService;
    private final PaymentRepository paymentRepository;
    private final MessageSource messageSource;


    public CheckPaymentValueValidator(CommitmentService commitmentService, PaymentRepository paymentRepository,
                                      MessageSource messageSource) {
        this.commitmentService = commitmentService;
        this.paymentRepository = paymentRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(PaymentDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getCommitmentId() == null) return false;

        var commitment = commitmentService.getById(dto.getCommitmentId()).orElseThrow();
        BigDecimal paymentSum = paymentRepository.sumPaymentsByCommitment(commitment.getId());
        var result = paymentSum.add(dto.getValue());
        boolean isValid = result.compareTo(commitment.getValue()) <= 0;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String message = messageSource.getMessage("sum.exceed.value.message",
                    new Object[]{"pagamentos"}, LocaleContextHolder.getLocale());

            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
