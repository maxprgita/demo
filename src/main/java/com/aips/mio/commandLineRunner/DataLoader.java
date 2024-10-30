package com.aips.mio.commandLineRunner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aips.mio.entity.Cliente;
import com.aips.mio.entity.Prodotto;
import com.aips.mio.repository.ClienteRepository;
import com.aips.mio.repository.ProdottoRepository;

	@Component
	public class DataLoader implements CommandLineRunner {

	    @Autowired
	    private ClienteRepository clienteRepository;

	    @Autowired
	    private ProdottoRepository prodottoRepository;

	    @Override
	    public void run(String... args) {
	        // Creazione dei clienti
	        Cliente customer1 = new Cliente();
	        customer1.setNome("Mario");
	        customer1.setCognome("Rossi");
	        customer1.setEmail("mario.rossi@example.com");

	        Cliente customer2 = new Cliente();
	        customer2.setNome("Luigi");
	        customer2.setCognome("Verdi");
	        customer2.setEmail("luigi.verdi@example.com");

	        Cliente customer3 = new Cliente();
	        customer3.setNome("Carla");
	        customer3.setCognome("Bianchi");
	        customer3.setEmail("carla.bianchi@example.com");

	        Cliente customer4 = new Cliente();
	        customer4.setNome("Giulia");
	        customer4.setCognome("Neri");
	        customer4.setEmail("giulia.neri@example.com");

	        Cliente customer5 = new Cliente();
	        customer5.setNome("Paolo");
	        customer5.setCognome("Gialli");
	        customer5.setEmail("paolo.gialli@example.com");

	        // Salvataggio dei clienti
	        clienteRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));
	   
	    
	        // Recupera i clienti dal database
	 /*       Cliente customer1 = clienteRepository.findById(1L).get();
	        Cliente customer2 = clienteRepository.findById(2L).get();
	        Cliente customer3 = clienteRepository.findById(3L).get();
	        Cliente customer4 = clienteRepository.findById(4L).get();
	        Cliente customer5 = clienteRepository.findById(5L).get();*/

	        // Creazione dei prodotti e associazione ai clienti
	        Prodotto product1 = new Prodotto();
	        product1.setPrezzo(100.50);
	        product1.setDescrizione("Laptop");
	        product1.setGenere("Elettronica");
	        product1.setCliente(customer1);

	        Prodotto product2 = new Prodotto();
	        product2.setPrezzo(50.75);
	        product2.setDescrizione("Smartphone");
	        product2.setGenere("Elettronica");
	        product2.setCliente(customer2);

	        Prodotto product3 = new Prodotto();
	        product3.setPrezzo(25.99);
	        product3.setDescrizione("Libro");
	        product3.setGenere("Educazione");
	        product3.setCliente(customer3);

	        Prodotto product4 = new Prodotto();
	        product4.setPrezzo(10.99);
	        product4.setDescrizione("Mouse");
	        product4.setGenere("Elettronica");
	        product4.setCliente(customer1);

	        Prodotto product5 = new Prodotto();
	        product5.setPrezzo(15.00);
	        product5.setDescrizione("Tastiera");
	        product5.setGenere("Elettronica");
	        product5.setCliente(customer4);

	        Prodotto product6 = new Prodotto();
	        product6.setPrezzo(100.00);
	        product6.setDescrizione("Cuffie");
	        product6.setGenere("Audio");
	        product6.setCliente(customer5);

	        Prodotto product7 = new Prodotto();
	        product7.setPrezzo(30.00);
	        product7.setDescrizione("Monitor");
	        product7.setGenere("Elettronica");
	        product7.setCliente(customer2);

	        // Salvataggio dei prodotti
	        prodottoRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7));

	    
	    
	    }
}
	
