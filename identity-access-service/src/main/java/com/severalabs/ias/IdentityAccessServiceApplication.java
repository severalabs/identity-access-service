package com.severalabs.ias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider") //Turn on JPA Auditing across app
public class IdentityAccessServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdentityAccessServiceApplication.class, args);
	}

}



