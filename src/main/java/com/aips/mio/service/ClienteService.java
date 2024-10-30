package com.aips.mio.service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.aips.mio.annotation.LogExecutionTime;
import com.aips.mio.dto.ClienteResponseDTO;
import com.aips.mio.dto.ProdottoResponseDTO;
import com.aips.mio.entity.Cliente;
import com.aips.mio.entity.Prodotto;
import com.aips.mio.interfaces.IServiceKafka;
import com.aips.mio.mapper.MapperCliente;
import com.aips.mio.messages.MessageService;
import com.aips.mio.repository.ClienteRepository;
import com.aips.mio.utility.ConversioneDiOggetti;
import com.aips.mio.utility.DateOra;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final MessageService messageService;
    private final IServiceKafka kafkaService;
    @Autowired
    private DateOra dateOra; 

    private final String patternOraData= "yyyy-MM-dd HH:mm:ss";
    
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, MessageService messageService, IServiceKafka kafkaService) {
        this.clienteRepository = clienteRepository;
        this.messageService = messageService;
		this.kafkaService = kafkaService;
    }
    
    
    @LogExecutionTime
    public List<ClienteResponseDTO> findAll(Locale locale) {
    	List<Cliente> clienti = clienteRepository.findAll();
        return new MapperCliente().Cliente_TO_ClienteResponseDTO(clienti, 
        														messageService, 
        														locale);
    }

    
    // Metodo per ottenere un cliente
    public ClienteResponseDTO getClienteById(Long id, Locale locale) {
    	Optional<Cliente> cliente = clienteRepository.findById(id);
    	if (cliente.isPresent()) {
    		// A questo punto, i prodotti non sono stati caricati perché è LAZY
            return new ClienteResponseDTO(cliente.get());
        }else {
            String messaggio = messageService.getMessaggio("cliente_non_trovato", locale);
            return new ClienteResponseDTO(messaggio);
        }
    }
    
    
    // Metodo per ottenere i prodotti di un cliente
    public List<ProdottoResponseDTO> getProdottiByClienteId(Long id, Locale locale) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            // Lazy loading: i prodotti vengono caricati qui
            List<Prodotto> prodotti = cliente.getProdotti();
            return new ConversioneDiOggetti()
            		.convertListProductIntoProdottoResponseDTO(prodotti);
        } else {
            String messaggio = messageService.getMessaggio("prodotto_non_trovato", locale);
            return Collections.singletonList(new ProdottoResponseDTO(messaggio));
        }
    }
    
    
    private Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }


    // Metodo per inserire un cliente e gestire la risposta
    public ResponseEntity<String> insertClienteWithMessage(Cliente cliente, Locale locale) {
        try {
            // Tentativo di salvare il cliente
            Cliente nuovoCliente = save(cliente);
            // Se l'inserimento ha successo, restituisce un messaggio con status 201 Created
            String messaggioConferma = messageService.getMessaggio("cliente_creato_ok", locale)+ " " + nuovoCliente.getNome() + " " + nuovoCliente.getCognome();;
 
            kafkaService.inviaMessaggio("cliente-events", "Nuovo cliente: " + nuovoCliente.getNome() 
            	+ " time: "+ dateOra.dataOraAdesso(patternOraData));
 
            return ResponseEntity.status(HttpStatus.CREATED).body(messaggioConferma);
        } catch (DataAccessException e) {
            // Errore legato ai dati, restituisce un messaggio di errore con status 400
            String messaggioErrore = messageService.getMessaggio("cliente_creato_ko", locale) + " " + "Dati non validi.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messaggioErrore);
        } catch (Exception e) {
            // Errore generico, restituisce un messaggio di errore con status 500
            String messaggioErrore = messageService.getMessaggio("cliente_creato_ko", locale) + " " + "Errore interno del server.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messaggioErrore);
        }

    }
}

