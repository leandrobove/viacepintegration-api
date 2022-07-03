package br.com.leandrobove.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.leandrobove.infraestructure.cache.CacheFactory;
import br.com.leandrobove.infraestructure.cache.CacheStrategy;
import br.com.leandrobove.infraestructure.cache.LocalCacheService;
import reactor.core.publisher.Mono;

@Service
public class CacheService {

	private CacheStrategy cacheStrategy;

	@Autowired
	private CacheFactory cacheFactory;
	
	@Value("${app-config.cache.strategy}")
	private String strategy;

	// FACTORY METHOD DESIGN PATTERN
	@Autowired
	private void setCacheStrategyFactory() {
		cacheStrategy = cacheFactory.createInstance(strategy);
	}

	public Mono<String> buscar(String key) {
		return cacheStrategy.buscar(key);
	}

	public Mono<Boolean> exists(String key) {
		return cacheStrategy.existeComAKey(key);
	}

	public Mono<String> salvar(String key, String value) {
		return cacheStrategy.salvar(key, value);
	}

	public void removeExpiredKeys() {
		if (cacheStrategy instanceof LocalCacheService) {
			((LocalCacheService) cacheStrategy).removeKeysExpiradas();
		}
	}

}
