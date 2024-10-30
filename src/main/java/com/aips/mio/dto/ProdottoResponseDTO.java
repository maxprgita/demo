package com.aips.mio.dto;

import com.aips.mio.entity.Prodotto;  

public class ProdottoResponseDTO {

    private Long id;
    private double prezzo;
    private String descrizione;
    private String genere;
    private String messaggio; // Messaggio da visualizzare in caso di errore o avviso

    // Costruttore con il messaggio (usato quando il prodotto non è trovato)
    public ProdottoResponseDTO(String messaggio) {
        this.messaggio = messaggio;
    }

    // Costruttore con i dati del prodotto (usato quando il prodotto è trovato)
    public ProdottoResponseDTO(Prodotto prodotto) {
        this.id = prodotto.getId();
        this.prezzo = prodotto.getPrezzo();
        this.descrizione = prodotto.getDescrizione();
        this.genere = prodotto.getGenere();
    }

 
    public ProdottoResponseDTO(Long id, double prezzo, String descrizione, String genere) {
        this.id = id;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.genere = genere;
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

    public String getMessaggio() {
        return messaggio;
    }

    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }
}

