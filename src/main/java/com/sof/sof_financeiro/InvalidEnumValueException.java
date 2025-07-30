package com.sof.sof_financeiro;

import com.sof.sof_financeiro.util.MessageUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Created By : Berkson Ximenes
 * Date : 30/07/2025
 **/

public class InvalidEnumValueException extends RuntimeException {
    private final String messageKey;
    private final Locale locale;
    private final Object[] args;

    public InvalidEnumValueException(String messageKey) {
        this(messageKey, null, LocaleContextHolder.getLocale());
    }

    public InvalidEnumValueException(String messageKey, Object[] args) {
        this(messageKey, args, LocaleContextHolder.getLocale());
    }

    public InvalidEnumValueException(String messageKey, Object[] args, Locale locale) {
        this.messageKey = messageKey;
        this.locale = locale;
        this.args = args;
    }

    public String getLocalizedMessage() {
        return MessageUtil.getMessageForLocale(messageKey, args, locale);
    }
}
