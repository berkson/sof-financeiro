package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import com.sof.sof_financeiro.repository.CommitmentRepository;
import com.sof.sof_financeiro.services.ExpenseService;
import com.sof.sof_financeiro.validations.annotations.CheckCommitmentValue;
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
public class CheckCommitmentValueValidator implements ConstraintValidator<CheckCommitmentValue, CommitmentDto> {

    private final ExpenseService expenseService;
    private final CommitmentRepository commitmentRepository;
    private final MessageSource messageSource;

    public CheckCommitmentValueValidator(ExpenseService expenseService, CommitmentRepository commitmentRepository,
                                         MessageSource messageSource) {
        this.expenseService = expenseService;
        this.commitmentRepository = commitmentRepository;
        this.messageSource = messageSource;
    }

    @Override
    public boolean isValid(CommitmentDto dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getExpense() == null || dto.getExpense().getId() == null) return false;

        var expense = expenseService.getById(dto.getExpense().getId()).orElseThrow();
        BigDecimal commitmentSum = commitmentRepository.sumCommitmentsByExpense(expense.getId());
        var result = commitmentSum.add(dto.getValue());
        boolean isValid = result.compareTo(expense.getValue()) <= 0;

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            String message = messageSource.getMessage("sum.exceed.value.message",
                    new Object[]{"empenhos"}, LocaleContextHolder.getLocale());

            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation();
        }

        return isValid;
    }
}
