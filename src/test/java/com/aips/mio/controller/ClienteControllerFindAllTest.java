package com.aips.mio.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aips.mio.annotation.LogExecutionTimeAspect;
import com.aips.mio.entity.Cliente;
import com.aips.mio.service.ClienteService;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerFindAllTest {

	private static final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);
	
    @Mock
    private ClienteService clienteService;  

    @InjectMocks
    private ClienteControllerFindAll clienteController;  

    @Test
    void testFindAll() {
    	List<Cliente> mockClientes = popolaDB();
        when(clienteService.findAll()).thenReturn(mockClientes);

        List<Cliente> result = clienteController.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("Mario"); // Rossi");
        assertThat(result.get(1).getNome()).isEqualTo("Luigi"); // Verdi");
        verify(clienteService, times(1)).findAll();
        
        logger.info("Verifica completata: il metodo findAll() è stato chiamato una sola volta.");
    }
    
    private List<Cliente> popolaDB(){
        Cliente cliente1 = new Cliente(1L, "Mario", "Rossi", "mario.rossi@example.com");
        Cliente cliente2 = new Cliente(2L, "Luigi", "Verdi", "luigi.verdi@example.com");
        List<Cliente> mockClientes = Arrays.asList(cliente1, cliente2);
        return mockClientes;
    }
}
