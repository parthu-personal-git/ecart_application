package com.shopping.ecartbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@ComponentScan(basePackages = "com.shopping.ecartbackend.service")
//@EntityScan("com.shopping.ecartbackend.*")
@EnableJpaRepositories(basePackages = "com.shopping.ecartbackend.service")
@ComponentScan("com.shopping.ecartbackend.dao")
@SpringBootApplication
public class EcartBackendApplication {

	public static void main(String[] args) {
		System.out.println("hii");
		SpringApplication.run(EcartBackendApplication.class, args);
	}

}