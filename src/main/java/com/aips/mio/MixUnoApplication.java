package com.aips.mio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.aips.mio.commandLineRunner.SampleService;

@SpringBootApplication
public class MixUnoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MixUnoApplication.class, args);
	}

    
/*    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            // Ottieni il bean di SampleService dal contesto Spring
            SampleService sampleService = ctx.getBean(SampleService.class);
            // Chiama il metodo che desideri monitorare
            sampleService.executeLongTask();
        };
    }*/
}
