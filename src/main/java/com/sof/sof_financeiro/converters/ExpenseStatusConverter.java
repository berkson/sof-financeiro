package com.sof.sof_financeiro.converters;

import com.sof.sof_financeiro.InvalidEnumValueException;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 30/07/2025
 **/

@Component
public class ExpenseStatusConverter implements Converter<String, ExpenseStatus> {

    @Override
    public @NonNull ExpenseStatus convert(@NonNull String source) {
        try {
            return ExpenseStatus.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("non.existing.status", new Object[]{"despesa"});
        }
    }
}
