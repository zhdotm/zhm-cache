package io.github.zhdotm.cache.core.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * 混合缓存
 *
 * @author zhihao.mao
 */

@Slf4j
@AllArgsConstructor
public class MultiCache implements Cache {

    @Getter
    private final String name;

    @Getter
    private final List<SortedCache> caches = new ArrayList<>();

    public synchronized Boolean hasInnerCaches() {

        return !caches.isEmpty();
    }

    public synchronized void addInnerCache(Integer sort, String cacheManagerName, Cache cache) {
        sort = Optional
                .ofNullable(sort)
                .orElse(Integer.MAX_VALUE);
        SortedCache sortedCache = new SortedCache(sort, cacheManagerName, cache);
        caches.add(sortedCache);
        caches.sort(Comparator.comparingInt(SortedCache::getSort));
    }

    @Override
    public Object getNativeCache() {

        return this;
    }

    @Override
    public ValueWrapper get(Object key) {
        List<SortedCache> noValueCaches = new ArrayList<>();
        for (SortedCache cache : caches) {
            ValueWrapper valueWrapper = cache.get(key);
            if (Objects.nonNull(valueWrapper)) {
                for (SortedCache noValueCache : noValueCaches) {
                    noValueCache.put(key, valueWrapper.get());
                }
                return valueWrapper;
            }
            noValueCaches.add(cache);
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        for (SortedCache cache : caches) {
            T value = cache.get(key, type);

            return value;
        }

        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        List<Cache> noValueCaches = new ArrayList<>();
        for (SortedCache cache : caches) {
            ValueWrapper valueWrapper = cache.get(key);
            if (Objects.nonNull(valueWrapper)) {
                T value = (T) valueWrapper.get();
                for (Cache noValueCache : noValueCaches) {
                    noValueCache.put(key, value);
                }

                return value;
            }
            noValueCaches.add(cache);
        }

        SortedCache cache = caches.get(0);
        T value = cache.get(key, valueLoader);
        for (int i = 1; i < noValueCaches.size(); i++) {
            Cache noValueCache = noValueCaches.get(i);
            noValueCache.put(key, value);
        }

        return value;
    }

    @Override
    public void put(Object key, Object value) {
        for (SortedCache cache : caches) {
            cache.put(key, value);
        }
    }

    @Override
    public void evict(Object key) {
        List<SortedCache> revertCaches = caches.stream()
                .sorted((cacheTemp1, cacheTemp2) -> cacheTemp2.getSort() - cacheTemp1.getSort())
                .collect(Collectors.toList());
        for (SortedCache cache : revertCaches) {
            cache.evict(key);
        }
    }

    @Override
    public void clear() {
        List<SortedCache> revertCaches = caches.stream()
                .sorted((cacheTemp1, cacheTemp2) -> cacheTemp2.getSort() - cacheTemp1.getSort())
                .collect(Collectors.toList());
        for (SortedCache cache : revertCaches) {
            cache.clear();
        }
    }

}
