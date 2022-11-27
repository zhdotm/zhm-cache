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

    List<SortedCacheManager> innerCacheManagers;
    Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    public MultiCacheManager(SortedCacheManager... sortedCacheManagers) {
        innerCacheManagers = Arrays
                .stream(sortedCacheManagers)
                .sorted(Comparator.comparingInt(SortedCacheManager::getSort))
                .collect(Collectors.toList());
    }

    @Override
    public Cache getCache(String name) {

        return this.cacheMap.computeIfAbsent(name, (cacheName) -> {
            MultiCache multiCache = new MultiCache(cacheName);
            for (SortedCacheManager innerCacheManager : innerCacheManagers) {
                Cache cache = innerCacheManager.getCache(cacheName);
                if (Objects.nonNull(cache)) {
                    multiCache.addInnerCache(innerCacheManager.getSort(), innerCacheManager.getName(), cache);
                }
            }
            
            return multiCache.hasInnerCaches() ? multiCache : null;
        });
    }

    @Override
    public Collection<String> getCacheNames() {

        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

}

