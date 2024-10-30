package com.aips.mio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aips.mio.entity.Prodotto;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long>{
	List<Prodotto> findByClienteId(Long clienteId);
	
    // Trova i prodotti per genere
    List<Prodotto> findByGenere(String genere);

    // Trova i prodotti con prezzo minore di un valore specificato
    List<Prodotto> findByPrezzoLessThan(Double prezzo);
	
}
