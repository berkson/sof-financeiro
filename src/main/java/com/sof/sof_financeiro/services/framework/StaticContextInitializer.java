package com.sof.sof_financeiro.services.framework;

import com.sof.sof_financeiro.util.MessageUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

@Service
public class StaticContextInitializer {
    private final MessageSource messageSource;

    public StaticContextInitializer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void load() {
        MessageUtil.setMessageSource(this.messageSource);
    }
}
