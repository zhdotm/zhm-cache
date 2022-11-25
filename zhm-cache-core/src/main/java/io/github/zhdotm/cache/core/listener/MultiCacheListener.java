package io.github.zhdotm.cache.core.listener;

import io.github.zhdotm.cache.core.event.CacheEvent;
import io.github.zhdotm.cache.core.event.CacheEvictEvent;
import io.github.zhdotm.cache.core.event.CacheRefreshEvent;
import io.github.zhdotm.cache.core.manager.MultiCacheManager;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.cache.Cache;
import org.springframework.context.ApplicationListener;

/**
 * 混合缓存监听器
 *
 * @author zhihao.mao
 */

@AllArgsConstructor
public class MultiCacheListener implements ApplicationListener<CacheEvent> {

    private final MultiCacheManager multiCacheManager;

    @Override
    public void onApplicationEvent(@NonNull CacheEvent event) {
        if (event instanceof CacheEvictEvent) {
            CacheEvictEvent cacheEvictEvent = (CacheEvictEvent) event;
            doEvict(cacheEvictEvent);
        }
        if (event instanceof CacheRefreshEvent) {
            CacheRefreshEvent cacheRefreshEvent = (CacheRefreshEvent) event;
            doRefresh(cacheRefreshEvent);
        }
    }

    public void doEvict(CacheEvictEvent cacheEvictEvent) {
        String cacheName = cacheEvictEvent.getCacheName();
        Object key = cacheEvictEvent.getKey();
        Cache cache = multiCacheManager.getCache(cacheName);
        cache.evict(key);
    }

    public void doRefresh(CacheRefreshEvent cacheRefreshEvent) {
        String cacheName = cacheRefreshEvent.getCacheName();
        Object key = cacheRefreshEvent.getKey();
        Object value = cacheRefreshEvent.getValue();
        Cache cache = multiCacheManager.getCache(cacheName);
        cache.put(key, value);
    }

}
