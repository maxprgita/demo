package com.aips.mio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double prezzo;
    private String descrizione;
    private String genere;

    // Relazione N a 1 con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Prodotto() {}
    
//	public Prodotto(Long id, double prezzo, String descrizione, String genere, Cliente cliente) {
	//public Prodotto(Long id, double prezzo, String descrizione, String genere) {

    public Prodotto(double prezzo, String descrizione, String genere, Cliente cliente) {
     //super();
	//	this.id = id;
		this.prezzo = prezzo;
		this.descrizione = descrizione;
		this.genere = genere;
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
    
    

}
