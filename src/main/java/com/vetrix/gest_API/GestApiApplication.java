package com.vetrix.gest_API;

import com.vetrix.gest_API.account.Account;
import com.vetrix.gest_API.account.AccountRepository;
import com.vetrix.gest_API.account.Role;
import com.vetrix.gest_API.image.FileStorageProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageProperties.class})
public class GestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestApiApplication.class, args);
	}

	@Bean
	CommandLineRunner run(AccountRepository repository){
		return args -> {
			repository.save(new Account("controleur@gmail.com","controleur", Role.CONTROLLER));
			repository.save(new Account("chef@gmail.com","chef", Role.CHEF));
			repository.save(new Account("charge@gmail.com","charge", Role.CHARGE));
			repository.save(new Account("ordre@gmail.com","ordre", Role.ORDRE));
		};
	}
}
