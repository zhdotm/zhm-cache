package io.github.zhdotm.cache.core.event;

import lombok.Getter;

import java.util.Map;

/**
 * 缓存刷新缓存事件
 *
 * @author zhihao.mao
 */

public class CacheRefreshEvent extends CacheEvent {

    @Getter
    private final String cacheName;

    @Getter
    private final Object key;

    @Getter
    private final Object value;

    public CacheRefreshEvent(Map<String, Object> source) {
        super(source);
        this.cacheName = (String) source.get("cacheName");
        this.key = source.get("key");
        this.value = source.get("value");
    }

}
