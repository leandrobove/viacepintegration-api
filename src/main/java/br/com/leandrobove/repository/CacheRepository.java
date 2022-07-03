package br.com.leandrobove.repository;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
public class CacheRepository {
	
	@Autowired
	private ReactiveRedisTemplate<String, String> reactiveRedisTemplate;
	
	@Value("${app-config.cache.ttl}")
	private Integer ttl;
	
	public Mono<Boolean> save(String key, String value) {
        return reactiveRedisTemplate
            .opsForValue()
            .set(key, value)
            .then(reactiveRedisTemplate.expire(key, Duration.ofSeconds(ttl)))
            .onErrorResume(throwable -> {
                log.error("Erro ao tentar salvar dados no Redis para a chave: {}", key, throwable);
                return Mono.just(false);
            });
    }

    public Mono<String> get(String key) {
        return reactiveRedisTemplate
            .opsForValue()
            .get(key)
            .onErrorResume(throwable -> {
                log.error("Erro ao tentar recuperar dados no Redis para a chave: {}", key, throwable);
                return Mono.empty();
            });
    }

    public Mono<Boolean> existsForKey(String key) {
        return reactiveRedisTemplate
            .hasKey(key)
            .onErrorResume(throwable -> {
                log.error("Erro ao tentar consultar dados no Redis para a chave: {}", key, throwable);
                return Mono.just(false);
            });
    }
}
