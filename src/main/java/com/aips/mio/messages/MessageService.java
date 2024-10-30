package com.aips.mio.messages;

import org.springframework.stereotype.Service;
import java.util.Locale;

@Service
public class MessageService {

    private final MessaggioLoader messaggioLoader;

    public MessageService(MessaggioLoader messaggioLoader) {
        this.messaggioLoader = messaggioLoader;
    }

    public String getMessaggio(String chiave, Locale locale) {
        String lingua = locale.getLanguage();
        messaggioLoader.caricaMessaggiPerLingua(lingua);

        return messaggioLoader.getMessaggio(chiave);
    }
}

