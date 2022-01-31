package com.fdmgroup.cartsApiService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class CartsApiServiceApplication {
	
	public static final String PRODUCT_API_URL = "http://localhost:8084";
	

	public static void main(String[] args) {
		SpringApplication.run(CartsApiServiceApplication.class, args);
	}
	
	@Bean
	public RestTemplate productsApiRestTemplate() {
		return new RestTemplateBuilder().rootUri(PRODUCT_API_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}

	@Bean
	public WebClient productsApiWebClient() {
		return WebClient.builder().baseUrl(PRODUCT_API_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
	}
	
	
	

}
