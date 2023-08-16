package com.jnjnetwork.CodeBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodeBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeBankApplication.class, args);
	}

}
