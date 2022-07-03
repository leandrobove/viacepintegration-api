package br.com.leandrobove.infraestructure.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.leandrobove.repository.CacheRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RedisCacheService implements CacheStrategy {
	
	@Autowired
	private CacheRepository cacheRepository;

	@Override
	public Mono<String> salvar(String key, String value) {
		try {
            return cacheRepository
                .save(key, value)
                .flatMap(saved -> {
                    if (saved) {
                        log.info("Cache salvo para a chave {}", key);
                    } else {
                        log.info("Não foi possível salvar cache para a chave {}", key);
                    }
                    return Mono.just(value);
                });
        } catch (Exception ex) {
            log.error("Erro ao tentar salvar cache para chave {}", key, ex);
        }
        return Mono.just(value);
	}

	@Override
	public Mono<String> buscar(String key) {
		try {
            return cacheRepository
                .get(key)
                .doOnNext(response -> log.info("Retornando cache para a chave {}", key));
        } catch (Exception ex) {
            log.error("Erro ao tentar recuperar cache para chave {}", key, ex);
        }
        return Mono.empty();
	}

	@Override
	public Mono<Boolean> existeComAKey(String key) {
		try {
            return cacheRepository
                .existsForKey(key)
                .doOnNext(exists -> {
                    if (exists) {
                        log.info("Cache existente para a chave {}", key);
                    } else {
                        log.info("Cache não existente para a chave {}", key);
                    }
                });
        } catch (Exception ex) {
            log.error("Erro ao tentar recuperar cache para chave {}", key, ex);
        }
        return Mono.just(false);
	}

}
