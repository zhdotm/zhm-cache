package io.github.zhdotm.cache.core.support;

import io.github.zhdotm.cache.core.event.CacheEvent;
import io.github.zhdotm.cache.core.spi.CacheEventListener;
import io.github.zhdotm.cache.core.spi.impl.MultiCacheEventListenerImpl;
import io.github.zhdotm.cache.core.util.SpringUtil;

import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * 混合缓存支持
 *
 * @author zhihao.mao
 */

public class MultiCacheSupport {

    private static MultiCacheSupport multiCacheSupport;

    private List<CacheEventListener> cacheEventListeners;

    public static synchronized MultiCacheSupport getInstance() {
        if (Objects.isNull(multiCacheSupport)) {
            multiCacheSupport = new MultiCacheSupport();
            multiCacheSupport.init();
        }

        return multiCacheSupport;
    }

    public void publishCacheEvent(CacheEvent cacheEvent) {
        if (Objects.isNull(cacheEventListeners) || cacheEventListeners.size() == 0) {
            init();
        }
        for (CacheEventListener cacheEventListener : cacheEventListeners) {
            cacheEventListener.onEvent(cacheEvent);
        }
    }

    private void init() {
        MultiCacheEventListenerImpl multiCacheEventListener = new MultiCacheEventListenerImpl();
        SpringUtil.registerSingleton(multiCacheEventListener.getName(), multiCacheEventListener);
        cacheEventListeners.add(multiCacheEventListener);
        ServiceLoader<CacheEventListener> serviceLoader = ServiceLoader.load(CacheEventListener.class);
        for (CacheEventListener cacheEventListener : serviceLoader) {
            cacheEventListeners.add(cacheEventListener);
        }
    }

}
