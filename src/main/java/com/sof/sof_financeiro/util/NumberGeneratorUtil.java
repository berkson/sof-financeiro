package com.sof.sof_financeiro.util;


import lombok.NonNull;

import java.time.LocalDate;
import java.util.Random;

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
        if (last.isEmpty()) {
            last = expectedPrefix + "0000";
        }

        String lastNumberStr = last.substring(expectedPrefix.length());
        int nextNumber = Integer.parseInt(lastNumberStr) + 1;

        String nextFormatted = String.format("%04d", nextNumber);

        return expectedPrefix + nextFormatted;
    }


    public static synchronized @NonNull String getNextProtocol(@NonNull String last) {
        Random random = new Random();
        String prefix = "43022";

        StringBuilder randomDigits = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            randomDigits.append(random.nextInt(10));
        }

        String ano = String.valueOf(LocalDate.now().getYear());

        String lastSequential;
        if (last.isEmpty()) {
            lastSequential = "00";
        } else {
            lastSequential = last.substring(last.length() - 2);
        }
        int nextSequential = Integer.parseInt(lastSequential) + 1;
        String sequencialFormatado = String.format("%02d", nextSequential);

        return prefix + randomDigits + ano + sequencialFormatado;
    }

}
