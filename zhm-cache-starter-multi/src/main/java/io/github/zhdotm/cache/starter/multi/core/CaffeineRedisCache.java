package io.github.zhdotm.cache.starter.multi.core;

import lombok.SneakyThrows;
import org.redisson.spring.cache.RedissonCache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @author zhihao.mao
 */

public class CaffeineRedisCache extends AbstractValueAdaptingCache {

    private final String name;

    private final CaffeineCache caffeineCache;

    private final RedissonCache redissonCache;


    public CaffeineRedisCache(String name, CaffeineCache caffeineCache, RedissonCache redissonCache) {
        this(name, caffeineCache, redissonCache, true);
    }

    public CaffeineRedisCache(String name, CaffeineCache caffeineCache, RedissonCache redissonCache,
                              boolean allowNullValues) {

        super(allowNullValues);
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(caffeineCache, "Caffeine Cache must not be null");
        Assert.notNull(redissonCache, "Redis Cache must not be null");
        this.name = name;
        this.caffeineCache = caffeineCache;
        this.redissonCache = redissonCache;
    }

    @Override
    protected Object lookup(Object key) {
        Object obj = caffeineCache
                .getNativeCache()
                .getIfPresent(key);
        if (Objects.nonNull(obj)) {

            return obj;
        }

        obj = redissonCache
                .getNativeCache()
                .get(key);
        if (Objects.nonNull(obj)) {
            caffeineCache
                    .getNativeCache()
                    .put(key, obj);
        }

        return obj;
    }

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public CaffeineRedisCache getNativeCache() {

        return this;
    }

    @SneakyThrows
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object lookup = lookup(key);
        if (Objects.nonNull(lookup)) {

            Object value = fromStoreValue(lookup);
            return (T) value;
        }
        T value = valueLoader.call();
        put(key, value);
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        redissonCache.put(key, value);
        caffeineCache.put(key, value);
    }

    @Override
    public void evict(Object key) {
        redissonCache.evict(key);
        caffeineCache.evict(key);
    }

    @Override
    public void clear() {
        redissonCache.clear();
        caffeineCache.clear();
    }

}
