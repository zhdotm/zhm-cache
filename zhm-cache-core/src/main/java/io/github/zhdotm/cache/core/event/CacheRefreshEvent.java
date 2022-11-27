package io.github.zhdotm.cache.core.event;

import lombok.Getter;
import lombok.ToString;

/**
 * 缓存刷新缓存事件
 *
 * @author zhihao.mao
 */

@ToString
public class CacheRefreshEvent implements CacheEvent {

    @Getter
    private final String eventName = "cacheRefreshEvent";

    @Getter
    private final String cacheName;

    @Getter
    private final Object key;

    @Getter
    private final Object value;

    public CacheRefreshEvent(String cacheName, Object key, Object value) {
        this.cacheName = cacheName;
        this.key = key;
        this.value = value;
    }

}
