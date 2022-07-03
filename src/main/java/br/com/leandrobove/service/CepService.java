package br.com.leandrobove.service;

import static org.springframework.util.ObjectUtils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leandrobove.client.ViaCepClient;
import br.com.leandrobove.dto.CepResponse;
import br.com.leandrobove.dto.exception.ViaCepException;
import br.com.leandrobove.exception.CepInvalidoException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CepService {

	@Autowired
	private ViaCepClient viaCepClient;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CacheService cacheService;

	public Mono<CepResponse> findByCep(String cep) {
		
		//verifica no cache se o cep existe
		return cacheService
		.exists(cep)
		.flatMap(exists -> {
			if(exists) {
				//se existir, busca no cache
				return cacheService.buscar(cep);
			} else {
				//se nao existir, busca na API externa e salva no cache
				return viaCepClient
						.findByCep(cep)
						.flatMap(response -> cacheService.salvar(cep, response));
			}
		}).flatMap((response) -> {
			return this.handleResponse(response, cep);
		});
	}

	public Mono<CepResponse> handleResponse(String response, String cep) {
		if (!isEmpty(response)) {

			try {
				// Verifica se está retornando erro no response body
				ViaCepException viaCepException = objectMapper.readValue(response, ViaCepException.class);

				if (viaCepException.getErro() == Boolean.TRUE) {
					//lança exception customizada
					return Mono.error(new CepInvalidoException(cep));
				} else {
					CepResponse cepResponse = objectMapper.readValue(response, CepResponse.class);

					return Mono.just(cepResponse);
				}

			} catch (Exception e) {
				log.error("Erro ao tentar converter resposta do CEP.", e);
			}
		}

		return Mono.empty();
	}

}
