package com.sof.sof_financeiro.api.v1.model;

import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/

@Getter
@Setter
@NoArgsConstructor
public class BaseDto {
    protected Long id;
    @Positive(message = "O Valor obrigatório e maior que zero")
    protected BigDecimal value;
}
