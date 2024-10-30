package com.aips.mio.mapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.aips.mio.dto.ClienteResponseDTO;
import com.aips.mio.entity.Cliente;
import com.aips.mio.messages.MessageService;

public class MapperCliente {

	public List<ClienteResponseDTO> Cliente_TO_ClienteResponseDTO(final List<Cliente> clienti,
			MessageService messageService, final Locale locale){
		
	    // Se la lista è vuota, restituisce un DTO con il messaggio
	    if (clienti.isEmpty()) {
	    	String messaggio = messageService.getMessaggio("cliente_non_trovato", locale);
	        return List.of(new ClienteResponseDTO(messaggio));
	    }

	    // Mappa ciascun Cliente a ClienteResponseDTO, con messaggio = null
	    return clienti.stream()
	                  .map(ClienteResponseDTO::new) // Usa il costruttore che accetta Cliente
	                  .collect(Collectors.toList());
	}
}
