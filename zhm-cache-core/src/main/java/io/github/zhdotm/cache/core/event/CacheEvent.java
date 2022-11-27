package io.github.zhdotm.cache.core.event;

/**
 * 缓存事件
 *
 * @author zhihao.mao
 */

public interface CacheEvent {

    /**
     * 获取事件名称
     *
     * @return 事件名称
     */
    String getEventName();
}
