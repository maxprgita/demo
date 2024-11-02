CREATE TABLE cliente (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE prodotto (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    prezzo DECIMAL(10, 2) NOT NULL,
    descrizione VARCHAR(255) NOT NULL,
    genere VARCHAR(255) NOT NULL,
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

