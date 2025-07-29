package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.services.ExpenseService;
import com.sof.sof_financeiro.validations.annotations.ExpenseCanBeExcluded;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Component
public class ExpenseCanBeExcludedValidator implements ConstraintValidator<ExpenseCanBeExcluded, Long> {




    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {

    }
}
