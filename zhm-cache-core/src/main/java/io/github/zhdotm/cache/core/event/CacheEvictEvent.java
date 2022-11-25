package io.github.zhdotm.cache.core.event;

import lombok.Getter;

import java.util.Map;

/**
 * 缓存驱逐事件
 *
 * @author zhihao.mao
 */

public class CacheEvictEvent extends CacheEvent {

    @Getter
    private final String cacheName;

    @Getter
    private final Object key;

    public CacheEvictEvent(Map<String, Object> source) {
        super(source);
        this.cacheName = (String) source.get("cacheName");
        this.key = source.get("key");
    }
}
