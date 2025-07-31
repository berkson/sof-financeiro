package com.sof.sof_financeiro.util;

import java.math.BigDecimal;

/**
 * Created By : Berkson Ximenes
 * Date : 30/07/2025
 **/

public class ValueUtil {

    public static BigDecimal adjustSum(BigDecimal originalValue, BigDecimal newValue, BigDecimal actualSum) {
        if (originalValue == null || newValue == null || actualSum == null) return actualSum;

        int comparing = originalValue.compareTo(newValue);
        if (comparing > 0) {
            return actualSum.subtract(originalValue.subtract(newValue));
        } else if (comparing < 0) {
            return actualSum.add(newValue.subtract(originalValue));
        } else {
            return actualSum;
        }
    }
}
