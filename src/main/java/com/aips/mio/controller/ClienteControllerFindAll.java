package com.aips.mio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aips.mio.entity.Cliente;
import com.aips.mio.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/clienti")
public class ClienteControllerFindAll {

    @Autowired
    private ClienteService clienteService;

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
    public List<Cliente> findAll() {
        return clienteService.findAll();
    }
}

