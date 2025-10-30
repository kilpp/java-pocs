package com.gk.poc.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class CaffeineConfig {

    @Bean
    public CacheManager cacheManager() {
        Map<String, Caffeine<Object, Object>> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("loan", loanCacheConfig());
        cacheConfigurations.put("account", accountCacheConfig());

        CaffeineMultiCache cacheManager = new CaffeineMultiCache();
        cacheManager.setCacheConfigurations(cacheConfigurations);
        return cacheManager;
    }

    private Caffeine<Object, Object> loanCacheConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES);
    }

    private Caffeine<Object, Object> accountCacheConfig() {
        return Caffeine.newBuilder()
                .initialCapacity(50)
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES);
    }
}
