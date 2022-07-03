package br.com.leandrobove.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ViaCepClient {

	@Autowired
	private WebClient webClient;

	@Value("${app-config.client.viacep}")
	private String viaCepUri;

	public Mono<String> findByCep(String cep) {
		String uri = this.buildUri(cep);
		
		log.info("Buscando dados do cep {} na API do ViaCep", cep);
		
		return webClient
			.get()
			.uri(uri)
			.retrieve()
			.bodyToMono(String.class)
			.onErrorResume(throwable -> {
				log.error("Erro ao tentar buscar dados do cep na API do ViaCep para o CEP {}", cep, throwable);
				return Mono.empty();
			})
			.doOnNext(response -> {
				log.info("Retornando dados da API do ViaCep para o cep {}: {}", cep, response);
			});
	}

	private String buildUri(String cep) {
		return String.format(viaCepUri, cep);
	}
}
