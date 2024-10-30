package com.aips.mio.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aips.mio.dto.ProdottoResponseDTO;
import com.aips.mio.entity.Prodotto;
import com.aips.mio.service.ProdottoService;
import com.aips.mio.utility.ConversioneDiOggetti;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/prodotti")
public class ProdottoController {

	@Autowired
	private ProdottoService prodottoService;
	private ConversioneDiOggetti co=new ConversioneDiOggetti();


	@Operation(
			summary = "Aggiungi un nuovo prodotto",
			description = "Aggiunge un nuovo prodotto nel sistema. Restituisce i dettagli del prodotto appena creato. In caso di errore, viene restituito un messaggio con la descrizione dell'errore.",
			tags = { "Prodotti" }
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prodotto aggiunto con successo", 
					content = @Content(schema = @Schema(implementation = ProdottoResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dati di richiesta non validi o messaggio di errore", 
			content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "500", description = "Errore interno del server")
	})
	@PostMapping
	public ResponseEntity<?> aggiungiProdotto(@RequestBody Prodotto prodotto, 
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		ProdottoResponseDTO nuovoProdottoSalvato = prodottoService.salvaProdotto(prodotto, 
																	co.handleLocale(locale));
		if (nuovoProdottoSalvato.getMessaggio() == null) 
			return ResponseEntity.ok(nuovoProdottoSalvato);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(nuovoProdottoSalvato.getMessaggio());
	}

	//---------------------------------------------------------------------------------

	@Operation(
			summary = "Aggiorna un prodotto",
			description = "Aggiorna i dettagli di un prodotto esistente nel sistema in base all'ID fornito. Restituisce i dettagli aggiornati del prodotto. In caso di errore, vengono restituiti i dettagli dell'errore.",
			tags = { "Prodotti" }
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prodotto aggiornato con successo", 
					content = @Content(schema = @Schema(implementation = ProdottoResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dati di richiesta non validi"),
			@ApiResponse(responseCode = "404", description = "Prodotto non trovato"),
			@ApiResponse(responseCode = "500", description = "Errore interno del server")
	})
	@PutMapping("/{id}")
	public ResponseEntity<?> aggiornaProdotto(@PathVariable Long id,
			@RequestBody Prodotto prodottoAggiornato, 
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		ProdottoResponseDTO prodottoDto = prodottoService.aggiornaProdotto(id, prodottoAggiornato, 
				co.handleLocale(locale));

		 return Optional.ofNullable(prodottoDto.getMessaggio())
		            .<ResponseEntity<?>>map(messaggio -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(messaggio))
		            .orElse(ResponseEntity.ok(prodottoDto));
	}

	//-----------------------------------------------------------------------------------------------

	@Operation(
			summary = "Elimina un prodotto",
			description = "Elimina un prodotto dal sistema in base all'ID fornito. In caso di successo, non viene restituito alcun contenuto. In caso di errore, vengono restituiti i dettagli dell'errore.",
			tags = { "Prodotti" }
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Prodotto eliminato con successo"),
			@ApiResponse(responseCode = "404", description = "Prodotto non trovato"),
			@ApiResponse(responseCode = "500", description = "Errore interno del server")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<String> cancellaProdotto(@PathVariable Long id,
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		String risposta=prodottoService.cancellaProdotto(id,co.handleLocale(locale));
		return new ResponseEntity<>(risposta, HttpStatus.OK);
	}

	//--------------------------------------------------------------------------------

	@Operation(
			summary = "Recupera la lista di tutti i prodotti",
			description = "Restituisce una lista di prodotti presenti nel sistema. In caso di errore, vengono restituiti i dettagli dell'errore.",
			tags = { "Prodotti" }
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Lista prodotti restituita con successo"),
			@ApiResponse(responseCode = "404", description = "Prodotti non trovati"),
			@ApiResponse(responseCode = "500", description = "Errore interno del server")
	})
	@GetMapping
	public ResponseEntity<List<Prodotto>> listaProdotti() {
	    return prodottoService.trovaTuttiProdotti()
	            .map(prodotti -> new ResponseEntity<>(prodotti, HttpStatus.OK))
	            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
	}

	//---------------------------------------------------------------------------------------

	@Operation(
			summary = "Trova un prodotto per ID",
			description = "Restituisce un prodotto corrispondente all'ID fornito, oppure un messaggio di errore se non viene trovato.",
			tags = { "Prodotti" }
			)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Prodotto trovato con successo",
					content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = ProdottoResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Prodotto non trovato",
			content = @Content(mediaType = "text/plain"))
	})
	@GetMapping("/{id}")
	public ResponseEntity<?> trovaProdottoPerId(@PathVariable Long id, 
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

		ProdottoResponseDTO risposta = prodottoService.trovaProdottoPerId(id, 
															co.handleLocale(locale));
	    return risposta.getMessaggio() == null 
	            ? ResponseEntity.ok(risposta) 
	            : ResponseEntity.status(HttpStatus.NOT_FOUND).body(risposta.getMessaggio());
	    }

//---------------------------------------------------------
	
	@Operation(
		    summary = "Trova prodotti per genere",
		    description = "Restituisce una lista di prodotti corrispondenti al genere fornito, oppure un messaggio di errore se non vengono trovati.",
		    tags = { "Prodotti" }
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Prodotti trovati con successo",
		        content = @Content(mediaType = "application/json", 
		        schema = @Schema(implementation = ProdottoResponseDTO.class))),
		    @ApiResponse(responseCode = "404", description = "Nessun prodotto trovato",
		        content = @Content(mediaType = "text/plain"))
		})
	@GetMapping("/genere/{genere}")
	public ResponseEntity<List<?>> trovaProdottiPerGenere(@PathVariable String genere, 
			@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
		
	    List<ProdottoResponseDTO> prodotti = prodottoService.trovaProdottiPerGenere(genere, 
	            co.handleLocale(locale));
	    
	    // Controlla se la lista contiene solo un messaggio di errore
	    if (prodotti.size() == 1 && prodotti.get(0).getMessaggio() != null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prodotti);
	    }

	    return ResponseEntity.ok(prodotti);
	}

	//------------------------------------------------------------------------
	
	@Operation(
		    summary = "Trova prodotti con prezzo inferiore",
		    description = "Restituisce una lista di prodotti con prezzo inferiore a quello fornito, oppure un messaggio di errore se non vengono trovati.",
		    tags = { "Prodotti" }
		)
		@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Prodotti trovati con successo",
		        content = @Content(mediaType = "application/json", 
		        schema = @Schema(implementation = ProdottoResponseDTO.class))),
		    @ApiResponse(responseCode = "404", description = "Nessun prodotto trovato",
		        content = @Content(mediaType = "text/plain"))
		})
	@GetMapping("/prezzoMinore/{prezzo}")
	public ResponseEntity<List<?>> trovaProdottiPerPrezzoMinore(
					@PathVariable Double prezzo, 
					@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
	
		List<ProdottoResponseDTO> prodotti = prodottoService.trovaProdottiPerPrezzoMinore(
														prezzo, co.handleLocale(locale));
	    // Controlla se la lista contiene solo un messaggio di errore
	    if (prodotti.size() == 1 && prodotti.get(0).getMessaggio() != null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(prodotti);
	    }

	    return ResponseEntity.ok(prodotti);
	}

}
