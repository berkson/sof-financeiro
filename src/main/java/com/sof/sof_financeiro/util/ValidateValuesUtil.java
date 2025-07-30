package com.sof.sof_financeiro.util;

import com.sof.sof_financeiro.shared.HasValue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class ValidateValuesUtil {
    private ValidateValuesUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    public static boolean isSetOfItemsValuesValid(BigDecimal value, List<? extends HasValue> items) {
        return compareSumOfValues(value, items) <= 0;
    }

    private static int compareSumOfValues(BigDecimal value, List<? extends HasValue> items) {
        BigDecimal sum = items.stream()
                .filter(Objects::nonNull)
                .map(HasValue::getValue)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sum.compareTo(value);
    }
}
