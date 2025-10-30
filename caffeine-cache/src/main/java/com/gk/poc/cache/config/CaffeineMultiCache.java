package com.gk.poc.cache.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CaffeineMultiCache implements CacheManager {

    private final Map<String, Cache> caches = new ConcurrentHashMap<>();
    private final Map<String, Caffeine<Object, Object>> cacheConfigurations = new HashMap<>();

    public void setCacheConfigurations(Map<String, Caffeine<Object, Object>> cacheConfigurations) {
        this.cacheConfigurations.clear();
        this.cacheConfigurations.putAll(cacheConfigurations);
    }

    @Override
    public Cache getCache(String name) {
        if (!caches.containsKey(name) && (!caches.containsKey(name))) {
                caches.put(name, createCache(name));
        }
        return caches.get(name);
    }

    @Override
    public Collection<String> getCacheNames() {
        return caches.keySet();
    }

    private Cache createCache(String name) {
        return new CaffeineCache(name, cacheConfigurations.get(name).build());
    }
}
