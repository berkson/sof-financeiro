package com.sof.sof_financeiro.util;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public class MessageUtil {
    private static MessageSource messageSource;

    public static String getMessageForLocale(String messageKey, Locale locale) {
        return ResourceBundle.getBundle("messages", locale).getString(messageKey);
    }

    public static String getMessageForLocale(String messageKey, Object[] args, Locale locale) {
        return messageSource.getMessage(messageKey, args, locale);
    }

    public static void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }
}
