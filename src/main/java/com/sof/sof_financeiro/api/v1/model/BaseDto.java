package com.sof.sof_financeiro.api.v1.model;

import com.sof.sof_financeiro.shared.HasValue;
import jakarta.validation.constraints.NotNull;
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
    @Positive(message = "{value.not.null}")
    @NotNull(message = "{value.not.null}")
    protected BigDecimal value;
}
