package com.cwiztech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.cwiztech.promotion"})
public class SpringmicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmicroserviceApplication.class, args);
	}

}
