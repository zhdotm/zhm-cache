package io.github.zhdotm.cache.core.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * 可排序缓存管理器
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class SortedCacheManager implements CacheManager {

    @Getter
    private final Integer sort;

    @Getter
    private final String name;

    @Getter
    private final CacheManager cacheManager;

    @Override
    public Cache getCache(String name) {

        return cacheManager.getCache(name);
    }

    @Override
    public Collection<String> getCacheNames() {

        return cacheManager.getCacheNames();
    }
}
