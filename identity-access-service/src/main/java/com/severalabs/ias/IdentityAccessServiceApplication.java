package com.severalabs.ias;

import com.severalabs.ias.domain.Role;
import com.severalabs.ias.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class IdentityAccessServiceApplication {

	@Autowired
	private static RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(IdentityAccessServiceApplication.class, args);
	}

}
