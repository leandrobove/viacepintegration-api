package br.com.leandrobove.infraestructure.cache;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LocalCacheService implements CacheStrategy {

	private static final String VALUE_FIELD = "value";
	private static final String EXPIRE_FIELD = "expire";
	private static final Map<String, Map<String, Object>> CACHE = new HashMap<>();

	@Value("${app-config.cache.ttl}")
	private Integer ttl;

	@Override
	public Mono<String> salvar(String key, String value) {
		try {
			if (!exists(key)) {
				var data = new HashMap<String, Object>();
				data.put(VALUE_FIELD, value);
				data.put(EXPIRE_FIELD, LocalDateTime.now().plusSeconds(ttl));
				CACHE.put(key, data);
				log.info("Salvando cache para chave {}", key);
			}
		} catch (Exception ex) {
			log.error("Erro ao tentar salvar cache para chave {}", key, ex);
		}
		return Mono.just(value);
	}

	@Override
	public Mono<String> buscar(String key) {
		try {
			if (exists(key)) {
				log.info("Cache existente para chave {}", key);
				var data = CACHE.get(key);
				log.info("Retornando json do cache para chave {}", key);
				return Mono.just((String) data.get(VALUE_FIELD));
			}
			log.info("Cache não encontrado para a chave {}", key);
		} catch (Exception ex) {
			log.error("Erro ao tentar encontrar cache para chave {}", key, ex);
		}
		return Mono.empty();
	}

	@Override
	public Mono<Boolean> existeComAKey(String key) {
		return Mono.just(exists(key));
	}

	private boolean exists(String key) {
		var exists = CACHE.containsKey(key);
		if (exists && isExpired((LocalDateTime) CACHE.get(key).get(EXPIRE_FIELD))) {
			remove(key);
			return false;
		}
		return exists;
	}

	private boolean isExpired(LocalDateTime expiration) {
		return LocalDateTime.now().isAfter(expiration);
	}

	private void remove(String key) {
		CACHE.remove(key);
	}

	public void removeKeysExpiradas() {
		var keysToRemove = CACHE.keySet().stream()
				.filter(key -> isExpired((LocalDateTime) CACHE.get(key).get(EXPIRE_FIELD)))
				.collect(Collectors.toList());

		keysToRemove.forEach(CACHE::remove);

		if (!keysToRemove.isEmpty()) {
			log.info("{} chaves removidas.", keysToRemove.size());
		}
	}

}
