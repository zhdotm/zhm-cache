package io.github.zhdotm.cache.starter.multi.core;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;

/**
 * @author zhihao.mao
 */

public class CaffeineRedisCacheManager implements CacheManager {

    @Override
    public Cache getCache(String name) {
        return null;
    }

    @Override
    public Collection<String> getCacheNames() {
        return null;
    }

}
