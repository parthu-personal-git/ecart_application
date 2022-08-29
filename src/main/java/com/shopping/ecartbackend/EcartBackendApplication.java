package com.shopping.ecartbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
public class EcartBackendApplication {

	public static void main(String[] args) {
		System.out.println("from main function : ");
		SpringApplication.run(EcartBackendApplication.class, args);
	}

}
