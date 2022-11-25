package io.github.zhdotm.cache.core.manager;

import io.github.zhdotm.cache.core.cache.MultiCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 混合缓存管理器
 *
 * @author zhihao.mao
 */

public class MultiCacheManager implements CacheManager {

    Map<String, SortedCacheManager> cacheManagerMap = new ConcurrentHashMap<>(16);


    public MultiCacheManager(SortedCacheManager... sortedCacheManagers) {
        for (SortedCacheManager sortedCacheManager : sortedCacheManagers) {
            cacheManagerMap.put(sortedCacheManager.getName(), sortedCacheManager);
        }
    }

    @Override
    public Cache getCache(String name) {

        MultiCache multiCache = new MultiCache();
        cacheManagerMap
                .entrySet()
                .stream()
                .filter(entry -> Objects.nonNull(entry.getValue().getCache(name)))
                .forEach(entry -> {
                    SortedCacheManager value = entry.getValue();
                    multiCache.addInnerCache(value.getSort(), value.getCache(name));
                });

        return multiCache.hasInnerCaches() ? multiCache : null;
    }

    @Override
    public Collection<String> getCacheNames() {
        List<String> cacheNames = new ArrayList<>();

        for (Map.Entry<String, SortedCacheManager> entry : cacheManagerMap.entrySet()) {
            Collection<String> cacheNamesTemp = entry.getValue().getCacheNames();
            cacheNames.addAll(cacheNamesTemp);
        }

        return cacheNames
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

}

