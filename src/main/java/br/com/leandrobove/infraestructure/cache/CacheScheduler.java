package br.com.leandrobove.infraestructure.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.leandrobove.service.CacheService;

@Component
public class CacheScheduler {
	
	@Autowired
	private CacheService cacheService;
	
	@Scheduled(cron = "${app-config.cache.scheduler}")
	public void removerKeysExpiradas() {
		cacheService.removeExpiredKeys();
	}

}
