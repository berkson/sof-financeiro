package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.services.CommitmentService;
import com.sof.sof_financeiro.validations.annotations.ExpenseCanBeExcluded;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Component
public class ExpenseCanBeExcludedValidator implements ConstraintValidator<ExpenseCanBeExcluded, Long> {

    private CommitmentService commitmentService;


    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null) {
            return false;
        }
        return !commitmentService.existsByExpenseId(id);
    }

    @Autowired
    public void setCommitmentService(CommitmentService commitmentService) {
        this.commitmentService = commitmentService;
    }
}
