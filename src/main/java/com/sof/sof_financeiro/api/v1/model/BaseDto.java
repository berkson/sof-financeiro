package com.sof.sof_financeiro.api.v1.model;

import com.sof.sof_financeiro.shared.HasValue;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/

@Getter
@Setter
@NoArgsConstructor
public class BaseDto implements HasValue {
    protected Long id;
    @Positive(message = "O Valor obrigatório e maior que zero")
    protected BigDecimal value;
}
