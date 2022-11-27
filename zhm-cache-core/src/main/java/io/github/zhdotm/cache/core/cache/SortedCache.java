package io.github.zhdotm.cache.core.cache;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * 可排序缓存
 *
 * @author zhihao.mao
 */

@Slf4j
public class SortedCache implements Cache {

    @Getter
    private final Integer sort;

    @Getter
    private final String cacheManagerName;

    @Getter
    private final String cacheName;

    @Getter
    private final Cache cache;

    public SortedCache(Integer sort, String cacheManagerName, Cache cache) {
        this.sort = sort;
        this.cacheManagerName = cacheManagerName;
        this.cacheName = cache.getName();
        this.cache = cache;
    }

    @Override
    public String getName() {

        return cacheName;
    }

    @Override
    public Object getNativeCache() {

        return this;
    }

    @Override
    public ValueWrapper get(Object key) {

        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {

        return cache.get(key, type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {

        return cache.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {

        cache.put(key, value);
    }

    @Override
    public void evict(Object key) {

        cache.evict(key);
    }

    @Override
    public void clear() {

        cache.clear();
    }

}
