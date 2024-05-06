package com.example.fzana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FzanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FzanaApplication.class, args);
	}

}
