package com.aips.mio.messages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class MessaggioLoader {

    private static final String DEFAULT_LANGUAGE = "it"; // Default a italiano
    private final Map<String, String> messaggi = new HashMap<>();

    @Value("${messaggi.path}")  
    private String messaggiPath;

    @PostConstruct
    public void init() {
        caricaMessaggiPerLingua(DEFAULT_LANGUAGE);
    }

    public void caricaMessaggiPerLingua(String lingua) {
        messaggi.clear();  
        String nomeFile = String.format(messaggiPath, lingua);

        try {
            Path filePath = new ClassPathResource(nomeFile).getFile().toPath();
            // Uso di una nuova mappa temporanea per caricare i messaggi
            Map<String, String> nuoviMessaggi = new HashMap<>();
            
            try (Stream<String> lines = Files.lines(filePath)) {
                lines.forEach(line -> {
                    String[] keyValue = line.split("=", 2);
                    if (keyValue.length == 2) {
                    	nuoviMessaggi.put(keyValue[0], keyValue[1]);
                    }
                });
            }
            // Se tutto va a buon fine, aggiorno la mappa dei messaggi esistente
            messaggi.clear();
            messaggi.putAll(nuoviMessaggi);
        } catch (IOException e) {
            System.err.println("Impossibile caricare il file per la lingua: " + lingua + ", caricamento del default.");
        }
    }

    public String getMessaggio(String chiave) {
        return messaggi.getOrDefault(chiave, "Messaggio non trovato");
    }
}


