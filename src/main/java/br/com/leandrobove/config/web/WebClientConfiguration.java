package br.com.leandrobove.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

	@Bean
	public WebClient webClientConfig() {
		WebClient webClient = WebClient.builder().build();

		return webClient;
	}
}
