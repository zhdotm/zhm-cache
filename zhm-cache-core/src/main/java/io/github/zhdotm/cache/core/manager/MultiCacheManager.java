package io.github.zhdotm.cache.core.manager;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 混合缓存管理器
 *
 * @author zhihao.mao
 */

public class MultiCacheManager implements CacheManager {

    Map<String, SortedCacheManager> cacheManagerMap = new ConcurrentHashMap<>(16);

    @Override
    public Cache getCache(String name) {

        return cacheManagerMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getSort()))
                .filter(entry -> Objects.nonNull(entry.getValue().getCache(name)))
                .findFirst()
                .map(entry -> entry.getValue().getCache(name))
                .orElse(null);
    }

    @Override
    public Collection<String> getCacheNames() {

        return cacheManagerMap
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue().getSort()))
                .filter(entry -> Objects.nonNull(entry.getValue().getCacheNames()))
                .findFirst()
                .map(entry -> entry.getValue().getCacheNames())
                .orElse(null);
    }

}

