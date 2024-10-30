package com.aips.mio.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aips.mio.dto.ClienteResponseDTO;
import com.aips.mio.dto.ProdottoResponseDTO;
import com.aips.mio.entity.Cliente;
import com.aips.mio.service.ClienteService;
import com.aips.mio.utility.ConversioneDiOggetti;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
      
    @Autowired
    private ConversioneDiOggetti co; 
    
    @Operation(
            summary = "Retrieve all clients",
            description = "Creates a new order for the given order request.",
            tags = {"order"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clienti trovati con successo"),
            @ApiResponse(responseCode = "404", description = "Nessun cliente trovato"),
            @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @GetMapping("/all")
    public ResponseEntity<List<?>> findAll(
		@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    	List<ClienteResponseDTO> risposta = clienteService.findAll(co.handleLocale(locale));
    	
        // Verifica se il primo elemento della lista contiene un messaggio d'errore
        if (!risposta.isEmpty() && risposta.get(0).getMessaggio() == null) {
            return ResponseEntity.ok(risposta);
        }

        // Se c'è un errore, ritorna la lista con un singolo DTO che contiene il messaggio
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(risposta);
    }
    
    //------------------------------------------------------------------------
    
    @Operation(
    	    summary = "Insert a new client",
    	    description = "Inserisce un nuovo cliente nel sistema.",
    	    tags = {"Clienti"}
    	)
    @ApiResponses(value = {
    	    @ApiResponse(responseCode = "201", description = "Cliente creato con successo"),
    	    @ApiResponse(responseCode = "400", description = "Dati di input non validi o mancanti"),
    	    @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @PostMapping("/insert")
    public ResponseEntity<String> insertCliente(@RequestBody Cliente cliente,
    		@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    	
        return clienteService.insertClienteWithMessage(cliente, co.handleLocale(locale));
    }
    
    //-------------------------------------------------------------------------
    
    @Operation(
    	    summary = "Retrieve a client by ID",
    	    description = "Recupera i dettagli di un cliente specifico tramite il suo ID.",
    	    tags = {"Clienti"}
    	)
    @ApiResponses(value = {
    	    @ApiResponse(responseCode = "200", description = "Cliente trovato con successo"),
    	    @ApiResponse(responseCode = "404", description = "Cliente non trovato"),
    	    @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id,
    		@RequestHeader(name = "Accept-Language", required = false) Locale locale) {

    	locale = locale == null ? Locale.ITALIAN : locale;
   	    ClienteResponseDTO risposta = clienteService.getClienteById(id, co.handleLocale(locale));

		if (risposta.getMessaggio() != null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(risposta);  
    	}
		return ResponseEntity.ok(risposta);

    }
    
    //--------------------------------------------------------------------------
    
    @Operation(
    	    summary = "Retrieve products by Client ID",
    	    description = "Recupera tutti i prodotti acquistati da un cliente specifico tramite il suo ID.",
    	    tags = {"Prodotti", "Clienti"}
    	)
    	@ApiResponses(value = {
    	    @ApiResponse(responseCode = "200", description = "Prodotti trovati con successo"),
    	    @ApiResponse(responseCode = "404", description = "Nessun prodotto trovato per il cliente specificato"),
    	    @ApiResponse(responseCode = "500", description = "Errore interno del server")
    })
    @GetMapping("/{id}/prodotti")
    public ResponseEntity<List<?>> getProdottiByClienteId(@PathVariable Long id,
    		@RequestHeader(name = "Accept-Language", required = false) Locale locale) {
    	
        List<ProdottoResponseDTO> risposta = clienteService.getProdottiByClienteId(id, co.handleLocale(locale));

        // Verifica se il primo elemento della lista contiene un messaggio d'errore
        if (!risposta.isEmpty() && risposta.get(0).getMessaggio() == null) {
            return ResponseEntity.ok(risposta);
        }

        // Se c'è un errore, ritorna la lista con un singolo DTO che contiene il messaggio
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(risposta);
     }
    
       
}
