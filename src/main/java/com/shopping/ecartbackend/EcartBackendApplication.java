package com.shopping.ecartbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class EcartBackendApplication {

	public static void main(String[] args) {
		System.out.println("from main function : ");
		SpringApplication.run(EcartBackendApplication.class, args);
	}

}
