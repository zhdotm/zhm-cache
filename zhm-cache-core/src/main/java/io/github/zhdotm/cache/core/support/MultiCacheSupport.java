package io.github.zhdotm.cache.core.support;

import io.github.zhdotm.cache.core.event.CacheEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 混合缓存支持
 *
 * @author zhihao.mao
 */

public class MultiCacheSupport implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MultiCacheSupport.applicationContext = applicationContext;
    }

    public static void publishCacheEvent(CacheEvent cacheEvent) {
        applicationContext.publishEvent(cacheEvent);
    }

}
