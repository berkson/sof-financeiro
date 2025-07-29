package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.services.CommitmentService;
import com.sof.sof_financeiro.services.PaymentService;
import com.sof.sof_financeiro.validations.annotations.CommitmentCanBeExcluded;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Component
public class CommitmentCanBeExcludedValidator implements ConstraintValidator<CommitmentCanBeExcluded, Long> {

    private PaymentService paymentService;


    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if(id == null) return false;
        return !paymentService.existsByCommitmentId(id);
    }

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
