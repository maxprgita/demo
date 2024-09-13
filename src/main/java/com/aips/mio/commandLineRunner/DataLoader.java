package com.aips.mio.commandLineRunner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.aips.mio.entity.Cliente;
import com.aips.mio.repository.ClienteRepository;

	@Component
	public class DataLoader implements CommandLineRunner {

	    @Autowired
	    private ClienteRepository clienteRepository;

	    @Override
	    public void run(String... args) {
	        // Inserting tuples
	        Cliente customer1 = new Cliente();
	        customer1.setId(1L);
	        customer1.setNome("Mario");
	        customer1.setCognome("Rossi");
	        customer1.setEmail("mario.rossi@example.com");

	        Cliente customer2 = new Cliente();
	        customer2.setId(2L);
	        customer2.setNome("Luigi");
	        customer2.setCognome("Verdi");
	        customer2.setEmail("luigi.verdi@example.com");

	        clienteRepository.saveAll(Arrays.asList(customer1, customer2));

	}
}
	
