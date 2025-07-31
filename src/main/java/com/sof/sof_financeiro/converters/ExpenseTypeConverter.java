package com.sof.sof_financeiro.converters;

import com.sof.sof_financeiro.InvalidEnumValueException;
import com.sof.sof_financeiro.enums.ExpenseType;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created By : Berkson Ximenes
 * Date : 30/07/2025
 **/

@Component
public class ExpenseTypeConverter implements Converter<String, ExpenseType> {


    @Override
    public @NonNull ExpenseType convert(@NonNull String source) {
        try {
            return ExpenseType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumValueException("non.existing.type", new Object[]{"despesa"});
        }
    }
}
