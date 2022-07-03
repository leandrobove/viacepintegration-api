package br.com.leandrobove.infraestructure.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class CacheFactory {

	@Autowired
	private ApplicationContext appContext;

	public CacheStrategy createInstance(String strategyImplementation) {
		if (strategyImplementation.equals("local")) {
			return appContext.getBean(LocalCacheService.class);
		} else if (strategyImplementation.equals("redis")) {
			return appContext.getBean(RedisCacheService.class);
		}
		throw new IllegalArgumentException(
				"Implementação de strategy de cache não suportada, verifique o arquivo application.yml");
	}
}
