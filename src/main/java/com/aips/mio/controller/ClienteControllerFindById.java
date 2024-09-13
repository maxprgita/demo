package com.aips.mio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aips.mio.entity.Cliente;
import com.aips.mio.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;

@RestController
@RequestMapping("/api/clienti")
public class ClienteControllerFindById {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Retrieve a client by ID")
    @GetMapping("/{id}")
    public Optional<Cliente> findById(@PathVariable Long id) {
        return clienteService.findById(id);
    }
}

