package io.github.zhdotm.cache.core.cache;

import io.github.zhdotm.cache.core.event.CacheRefreshEvent;
import io.github.zhdotm.cache.core.support.MultiCacheSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * 混合缓存
 *
 * @author zhihao.mao
 */

@Slf4j
@AllArgsConstructor
public class MultiCache implements Cache {

    @Getter
    private final List<SortedCache> caches = new ArrayList<>();

    public synchronized Boolean hasInnerCaches() {

        return !caches.isEmpty();
    }

    public synchronized void addInnerCache(Integer sort, Cache cache) {
        sort = Optional
                .ofNullable(sort)
                .orElse(Integer.MAX_VALUE);
        SortedCache sortedCache = new SortedCache(sort, cache);
        caches.add(sortedCache);
        caches.sort(Comparator.comparingInt(SortedCache::getSort));
    }

    @Override
    public String getName() {

        return caches.get(0).getName();
    }

    @Override
    public Object getNativeCache() {

        return caches.get(0).getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {

        return caches.stream()
                .filter(sortedCache -> Objects.nonNull(sortedCache.get(key)))
                .findFirst()
                .map(sortedCache -> sortedCache.get(key))
                .orElse(null);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {

        return caches.stream()
                .filter(sortedCache -> Objects.nonNull(sortedCache.get(key, type)))
                .findFirst()
                .map(sortedCache -> sortedCache.get(key, type))
                .orElse(null);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {

        SortedCache cache = caches.get(0);
        String cacheName = getName();
        T value = cache.get(key, valueLoader);
        Map<String, Object> map = new HashMap<>();
        map.put("cacheName", cacheName);
        map.put("key", key);
        map.put("value", value);
        CacheRefreshEvent cacheRefreshEvent = new CacheRefreshEvent(map);
        MultiCacheSupport.publishCacheEvent(cacheRefreshEvent);

        return caches.stream()
                .filter(sortedCache -> Objects.nonNull(sortedCache.get(key, valueLoader)))
                .findFirst()
                .map(sortedCache -> sortedCache.get(key, valueLoader))
                .orElse(null);
    }

    @Override
    public void put(Object key, Object value) {
        for (SortedCache cache : caches) {
            cache.put(key, value);
        }
    }

    @Override
    public void evict(Object key) {
        for (SortedCache cache : caches) {
            cache.evict(key);
        }
    }

    @Override
    public void clear() {
        for (SortedCache cache : caches) {
            cache.clear();
        }
    }

}
