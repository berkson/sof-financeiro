package com.sof.sof_financeiro.validations.annotations;

import com.sof.sof_financeiro.validations.validators.CheckCommitmentValueValidator;
import com.sof.sof_financeiro.validations.validators.CheckPaymentValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPaymentValueValidator.class)
@Documented
public @interface CheckPaymentValue {
    String message() default "{sum.exceed.value.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
