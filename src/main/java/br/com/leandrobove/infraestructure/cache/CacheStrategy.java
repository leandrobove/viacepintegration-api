package br.com.leandrobove.infraestructure.cache;

import reactor.core.publisher.Mono;

public interface CacheStrategy {

	Mono<String> salvar(String key, String value);

	Mono<String> buscar(String key);

	Mono<Boolean> existeComAKey(String key);
}
