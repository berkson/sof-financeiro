package com.sof.sof_financeiro.util;

import com.sof.sof_financeiro.api.v1.model.BaseDto;

import java.math.BigDecimal;
import java.util.List;

public final class ValidateValuesUtil {
    private ValidateValuesUtil() {
        throw new IllegalArgumentException("StringUtil Class");
    }

    private static boolean isSetOfItemsValuesValid(BigDecimal value, List<BaseDto> items) {
        BigDecimal soma = BigDecimal.ZERO;
        for (var item : items) {
            soma = soma.add(item.getValue());
        }
        int check = soma.compareTo(value);
        return check <= 0;
    }
}
