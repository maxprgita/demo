package com.aips.mio.dto;

import com.aips.mio.entity.Cliente;

public class ClienteResponseDTO {

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private String messaggio;  // Campo aggiuntivo

    // Costruttore con il messaggio (usato quando il prodotto non è trovato)
    public ClienteResponseDTO(String messaggio) {
        this.messaggio = messaggio;
    }
    
    // Constructor che accetta un oggetto Cliente
    public ClienteResponseDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.cognome = cliente.getCognome();
        this.email = cliente.getEmail();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessaggio() { return messaggio; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }
}

