package io.github.zhdotm.cache.core.spi.impl;

import io.github.zhdotm.cache.core.event.CacheEvent;
import io.github.zhdotm.cache.core.event.CacheEvictEvent;
import io.github.zhdotm.cache.core.event.CacheRefreshEvent;
import io.github.zhdotm.cache.core.manager.MultiCacheManager;
import io.github.zhdotm.cache.core.spi.CacheEventListener;
import io.github.zhdotm.cache.core.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.Objects;

/**
 * default multi cache listener
 *
 * @author zhihao.mao
 */

@Slf4j
public class MultiCacheEventListenerImpl implements CacheEventListener {

    @Override
    public String getName() {
        
        return "multiCacheEventListener";
    }

    @Override
    public void onEvent(CacheEvent event) {
        log.info("receive {}: {}", event.getEventName(), event);

        if (event instanceof CacheEvictEvent) {
            CacheEvictEvent cacheEvictEvent = (CacheEvictEvent) event;
            onCacheEvictEvent(cacheEvictEvent);
        }

        if (event instanceof CacheRefreshEvent) {
            CacheRefreshEvent cacheRefreshEvent = (CacheRefreshEvent) event;
            onCacheRefreshEvent(cacheRefreshEvent);
        }
    }

    public void onCacheEvictEvent(CacheEvictEvent cacheEvictEvent) {
        String cacheName = cacheEvictEvent.getCacheName();
        Object key = cacheEvictEvent.getKey();
        Cache cache = getMultiCache(cacheName);
        if (Objects.nonNull(cache)) {
            cache.evict(key);
        }
    }

    public void onCacheRefreshEvent(CacheRefreshEvent cacheRefreshEvent) {
        String cacheName = cacheRefreshEvent.getCacheName();
        Object key = cacheRefreshEvent.getKey();
        Object value = cacheRefreshEvent.getValue();
        Cache cache = getMultiCache(cacheName);
        if (Objects.nonNull(cache)) {
            cache.put(key, value);
        }
    }

    private Cache getMultiCache(String cacheName) {
        MultiCacheManager multiCacheManager = SpringUtil.getBean(MultiCacheManager.class);

        return multiCacheManager.getCache(cacheName);
    }

}
