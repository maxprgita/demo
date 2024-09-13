package com.aips.mio.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.aips.mio.entity.Cliente;

@SpringBootTest
public class ClienteServiceIntegrationTest {

    @Autowired
    private ClienteService clienteService;

    @Test
    void testFindAllClients() {
        List<Cliente> clienti = clienteService.findAll();
        assertNotNull(clienti);
        assertEquals(2, clienti.size());
        assertEquals("Mario", clienti.get(0).getNome());
        assertEquals("Luigi", clienti.get(1).getNome());
    }
    
    @Test
    void testFindByIdClienteEsistente() {
        Optional<Cliente> cliente = clienteService.findById(1L);
        assertTrue(cliente.isPresent());
        assertEquals("Mario", cliente.get().getNome());
    }

    @Test
    void testFindByIdClienteNonEsistente() {
        Optional<Cliente> cliente = clienteService.findById(3L);
        assertFalse(cliente.isPresent());
    }
    
}
