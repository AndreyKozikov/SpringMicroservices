package org.example.WebMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.example.WebMicroService.client")
public class WebMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebMicroServiceApplication.class, args);
	}

}
