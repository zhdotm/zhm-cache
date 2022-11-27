package io.github.zhdotm.cache.core.event;

import lombok.Getter;
import lombok.ToString;

/**
 * 缓存驱逐事件
 *
 * @author zhihao.mao
 */

@ToString
public class CacheEvictEvent implements CacheEvent {

    @Getter
    private final String eventName = "cacheEvictEvent";

    @Getter
    private final String cacheName;

    @Getter
    private final Object key;

    public CacheEvictEvent(String cacheName, Object key) {
        this.cacheName = cacheName;
        this.key = key;
    }

}
