package com.daiquiriclub.daiquiriclub_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DaiquiriclubEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaiquiriclubEcommerceApplication.class, args);
	}

}
