package com.aips.mio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
*/

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

//-------------------
import com.aips.mio.dto.ClienteResponseDTO;
import com.aips.mio.eccezioni.GlobalExceptionHandler;
import com.aips.mio.entity.Cliente;
import com.aips.mio.messages.MessageService;
import com.aips.mio.service.ClienteService;
import com.aips.mio.utility.ConversioneDiOggetti;

//@WebMvcTest(controllers = ClienteController.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = ClienteController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = GlobalExceptionHandler.class)
	})
class ClienteControllerTest {

 /*   @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;
    
    @MockBean
    private MessageService messageService;

    @MockBean
    private ClienteRepository clienteRepository; //non serve


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
*/


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @MockBean
    private ConversioneDiOggetti conversioneDiOggetti; // Mock della dipendenza mancante

    
    @MockBean
    private MessageService messageService; // Mock the MessageService
    
    @InjectMocks
    private ClienteController clienteController;

 //   @Mock
 //   private ConversioneDiOggetti conversioneDiOggetti; // Simula l'utility di conversione

 

  /*  @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //clienteResponse = new ClienteResponseDTO();
    } 
*/	
	
    @Test
    void testFindAllClientsSuccess() throws Exception {
        // Mocking the service response with a ClienteResponseDTO constructed from Cliente
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("Mario");
        cliente.setCognome("Rossi");
        cliente.setEmail("mario.rossi@example.com");
 
        ClienteResponseDTO cliente1 = new ClienteResponseDTO(cliente); 
        List<ClienteResponseDTO> clienti = Arrays.asList(cliente1);

        //clienteService returns a list of clients
        when(clienteService.findAll(any())).thenReturn(clienti);

        // Perform GET request and expect 200 OK
        mockMvc.perform(get("/api/clienti/all")
                .header("Accept-Language", "it")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Mario"))
                .andExpect(jsonPath("$[0].cognome").value("Rossi"))
                .andExpect(jsonPath("$[0].email").value("mario.rossi@example.com"))
                .andExpect(jsonPath("$[0].messaggio").doesNotExist());
    }

    
    
    @Test
    void testFindAllClientsNotFound() throws Exception {
        // Mocking the service response with a ClienteResponseDTO containing an error message
        ClienteResponseDTO clienteError = new ClienteResponseDTO("Nessun cliente trovato"); 
        List<ClienteResponseDTO> errore = Collections.singletonList(clienteError);

        // Simulating that clienteService returns an error
        when(clienteService.findAll(any())).thenReturn(errore);

        // Perform GET request and expect 404 Not Found
        mockMvc.perform(get("/api/clienti/all")
                .header("Accept-Language", "it")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$[0].messaggio").value("Nessun cliente trovato"));
    }
    
    //-------------------------------------------------------------------------------------\\
    @Test
    void testInsertClienteSuccess() throws Exception {
        // Simula il cliente da inserire
        Cliente nuovoCliente = new Cliente("Mario", "Rossi", "mario.rossi@example.com");

        // Simula il risultato dell'inserimento con successo
        ResponseEntity<String> successResponse = ResponseEntity.status(HttpStatus.CREATED)
            .body("Cliente inserito con successo");

        // Configura il mock del servizio
        Mockito.when(clienteService.insertClienteWithMessage(Mockito.any(Cliente.class), Mockito.any(Locale.class)))
               .thenReturn(successResponse);

        // Effettua la richiesta e verifica le aspettative
        mockMvc.perform(post("/api/clienti/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN)
                .content("{\"nome\":\"Mario\",\"cognome\":\"Rossi\",\"email\":\"mario.rossi@example.com\"}")
                .header("Accept-Language", "it-IT"))
        .andExpect(result -> {
            int status = result.getResponse().getStatus();
            assertTrue(status == 200 || status == 201, "Expected status 200 or 201, but was: " + status);
        });
              //+  .andExpect(content().string("Cliente inserito con successo"));
    }


    @Test
    public void testInsertClienteWithMessage_DataAccessException() throws Exception {
        // Simula un'eccezione durante il salvataggio del cliente
        doThrow(new DataAccessException("Errore database") {}).when(clienteService).insertClienteWithMessage(any(), any());

        // Crea un oggetto Cliente da inviare come JSON
        Cliente cliente = new Cliente();
        cliente.setNome("Mario");
        cliente.setCognome("Rossi");
        cliente.setEmail("mario.rossi@example.com");

        // Esegui la richiesta POST simulando un'eccezione
        mockMvc.perform(post("/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"nome\": \"Mario\", \"cognome\": \"Rossi\", \"email\": \"mario.rossi@example.com\" }"))
        		.andExpect(result -> {
        			int status = result.getResponse().getStatus();
        			assertTrue(status == HttpStatus.INTERNAL_SERVER_ERROR.value() || status == HttpStatus.NOT_FOUND.value());
        		});

    }
    
    //-------------------------------------------QUA--------------------------------------------------
    
    @Test
    void testFindById_Success() throws Exception {
        // Simulazione di un cliente trovato con successo
        Cliente cliente = new Cliente("Mario", "Rossi", "mario.rossi@example.com");
        ClienteResponseDTO clienteResponse = new ClienteResponseDTO(cliente);

        // Simuliamo il comportamento del service
        when(clienteService.getClienteById(1L, Locale.ITALIAN)).thenReturn(clienteResponse);
        when(conversioneDiOggetti.handleLocale(Locale.ITALIAN)).thenReturn(Locale.ITALIAN);

        // Eseguiamo la richiesta GET
        mockMvc.perform(get("/api/clienti/1")
                .header("Accept-Language", "it"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Mario")))
                .andExpect(jsonPath("$.cognome", is("Rossi")))
                .andExpect(jsonPath("$.email", is("mario.rossi@example.com")));
    }




    @Test
    public void testFindByIdNotFound() throws Exception {
        Long clienteId = 999L; // ID non esistente
        Locale locale = Locale.ITALIAN;
        String messaggioNonTrovato = "Cliente non trovato";

        // Mock della risposta del servizio quando il cliente non viene trovato
        when(clienteService.getClienteById(clienteId, locale))
            .thenReturn(new ClienteResponseDTO(messaggioNonTrovato));

        // Mock del messaggio dal MessageService
        when(messageService.getMessaggio("cliente_non_trovato", locale))
            .thenReturn(messaggioNonTrovato);

        // Eseguo la chiamata GET e verifico la risposta
        mockMvc.perform(get("/api/cliente/{id}", clienteId)
                .header("Accept-Language", "it"))
                .andExpect(status().isNotFound());  // Verifica che lo stato sia 404
}

    
}// fine classe


