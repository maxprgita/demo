package com.aips.mio.service;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aips.mio.entity.Prodotto;
import com.aips.mio.messages.MessageService;
import com.aips.mio.repository.ProdottoRepository;

@Component
public class ProdottoValidator {

    @Autowired
    private MessageService messageService;
    
    @Autowired
    private ProdottoRepository prodottoRepository;

    
    public Optional<String> verificaErrori(Prodotto prodotto, Locale locale) {
        // Verifica se il prodotto ha già un ID ed esiste nel database
        if (prodotto.getId() != null && prodottoRepository.existsById(prodotto.getId())) {
            return Optional.of(messageService.getMessaggio("Non è possibile aggiornare un prodotto esistente.", locale));
        }

        // Verifica se il prodotto non passa le validazioni
        if (validazione(prodotto)) {
            return Optional.of(messageService.getMessaggio("Il nome del prodotto non può essere vuoto.", locale));
        }

        // Verifica se il prezzo del prodotto è valido
        if (prodotto.getPrezzo() <= 0) {
            return Optional.of(messageService.getMessaggio("Il prezzo del prodotto deve essere positivo.", locale));
        }

        return Optional.empty();
    }


    
    private boolean validazione(Prodotto prodotto) {
        return prodotto.getGenere() == null || prodotto.getGenere().isEmpty() || 
               prodotto.getDescrizione() == null || prodotto.getDescrizione().isEmpty();
    }

    
}

