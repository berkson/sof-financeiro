package com.sof.sof_financeiro.util;


import lombok.NonNull;

import java.time.LocalDate;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/

public final class NumberGeneratorUtil {

    private NumberGeneratorUtil() {
        throw new IllegalArgumentException("StringUtil Class");
    }

    public static synchronized @NonNull String getNextNumber(@NonNull String pattern, @NonNull String last) {
        String year = String.valueOf(LocalDate.now().getYear());

        String expectedPrefix = year + pattern;
        if (!last.startsWith(expectedPrefix)) {
            throw new IllegalArgumentException("Último número não corresponde ao padrão esperado: " + expectedPrefix);
        }

        String lastNumberStr = last.substring(expectedPrefix.length());
        int nextNumber = Integer.parseInt(lastNumberStr) + 1;

        String nextFormatted = String.format("%04d", nextNumber);

        return expectedPrefix + nextFormatted;
    }

}
