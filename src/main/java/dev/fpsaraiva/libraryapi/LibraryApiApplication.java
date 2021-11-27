package dev.fpsaraiva.libraryapi;

import dev.fpsaraiva.libraryapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class LibraryApiApplication {

	//Envio de emails desabilitado para testar outras partes da aplicação
	/*@Autowired
	private EmailService emailService;

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<String> emails = Arrays.asList("");
			emailService.sendMails("Testando serviço de emails", emails);
			System.out.println("LOG: EMAILS ENVIADOS");
		};
	};*/

	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
