package com.aips.mio.service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aips.mio.dto.ProdottoResponseDTO;
import com.aips.mio.entity.Prodotto;
import com.aips.mio.messages.MessageService;
import com.aips.mio.repository.ProdottoRepository;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoValidator prodottoValidator;
    
	private final ProdottoRepository prodottoRepository;
	private final MessageService messageService;

	public ProdottoService(ProdottoRepository prodottoRepository, MessageService messageService) {
		this.prodottoRepository = prodottoRepository;
		this.messageService = messageService;
	}


	public List<Prodotto> trovaProdottiPerCliente(Long clienteId) {
		return prodottoRepository.findByClienteId(clienteId);
	}


	public ProdottoResponseDTO aggiornaProdotto(Long id, Prodotto prodottoAggiornato, Locale locale) {
		Optional<Prodotto> prodottoEsistenteOpt = prodottoRepository.findById(id);
		if (prodottoEsistenteOpt.isPresent()) {
			Prodotto prodottoEsistente = prodottoEsistenteOpt.get(); 
			// Aggiorna i campi del prodotto esistente
			prodottoEsistente.setPrezzo(prodottoAggiornato.getPrezzo());
			prodottoEsistente.setDescrizione(prodottoAggiornato.getDescrizione());
			prodottoEsistente.setGenere(prodottoAggiornato.getGenere());

			// Salva e restituisci il prodotto aggiornato
			return new ProdottoResponseDTO(prodottoRepository.save(prodottoEsistente));
		}else {
			String messaggio = messageService.getMessaggio("prodotto_non_trovato", locale);
			return new ProdottoResponseDTO(messaggio);
		}
	}

	//------------------------------------------------------------------------

	public String cancellaProdotto(Long id, Locale locale) {
		return prodottoRepository.findById(id)
				.map(prodotto -> {
					prodottoRepository.deleteById(id);
					return messageService.getMessaggio("prodotto_cancellato", locale);
				})
				.orElseGet(() -> messageService.getMessaggio("prodotto_non_trovato", locale));
	}

//-------------------------------------------------------------------
	
	public ProdottoResponseDTO salvaProdotto(Prodotto prodotto, Locale locale) {
	    return prodottoValidator.verificaErrori(prodotto, locale)
	        .map(ProdottoResponseDTO::new) // Se c'è un errore, crea un DTO con il messaggio di errore
	        .orElseGet(() -> new ProdottoResponseDTO(prodottoRepository.save(prodotto))); // Altrimenti, salva il prodotto e restituisci il DTO
	}

//------------------------------------------------------------
	
	public Optional<List<Prodotto>> trovaTuttiProdotti() {
	    List<Prodotto> prodotti = prodottoRepository.findAll();
	    return prodotti.isEmpty() ? Optional.empty() : Optional.of(prodotti);
	}

//----------------------------------------------------------
	
	public ProdottoResponseDTO trovaProdottoPerId(Long id, Locale locale) {
	    return prodottoRepository.findById(id)
	        .map(ProdottoResponseDTO::new) // Se il prodotto è presente, crea il DTO con il prodotto
	        .orElseGet(() -> new ProdottoResponseDTO(messageService.getMessaggio("prodotto_non_trovato", locale))); // Altrimenti, restituisci il messaggio di errore
	}

	//------------------------------------------------------------------------------

	public List<ProdottoResponseDTO> trovaProdottiPerGenere(String genere, Locale locale) {
	    List<Prodotto> prodotti = prodottoRepository.findByGenere(genere);
	    
	    if (prodotti.isEmpty()) {
	        return Collections.singletonList(
	        		new ProdottoResponseDTO(messageService.getMessaggio("prodotto_non_trovato", locale)));
	    }

	    // Mappa ogni Prodotto a ProdottoResponseDTO
	    return prodotti.stream()
	        .map(ProdottoResponseDTO::new)
	        .collect(Collectors.toList());
	}


	//---------------------------------------------------------------------
	
	public List<ProdottoResponseDTO> trovaProdottiPerPrezzoMinore(Double prezzo, Locale locale) {
	    List<Prodotto> prodotti = prodottoRepository.findByPrezzoLessThan(prezzo);
	    
	    if (prodotti.isEmpty()) {
	        String messaggio = messageService.getMessaggio("nessun_prodotto_trovato", locale);
	        return Collections.singletonList(new ProdottoResponseDTO(messaggio));
	    }

	    // Mappa ogni Prodotto trovato a un ProdottoResponseDTO e restituisci la lista
	    return prodotti.stream()
	                   .map(ProdottoResponseDTO::new)
	                   .collect(Collectors.toList());
	}

}
