package com.conexa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Aplicacao {

	public static void main(final String[] args) {
		SpringApplication.run(Aplicacao.class, args);
	}
}
