package com.aips.mio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aips.mio.entity.Cliente;
import com.aips.mio.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/clienti")
public class ClienteControllerInsert {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Insert a new client")
    @PostMapping("/insert")
    public Cliente insertCliente(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }
}

