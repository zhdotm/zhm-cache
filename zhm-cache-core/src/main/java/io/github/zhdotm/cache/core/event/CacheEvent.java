package io.github.zhdotm.cache.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhihao.mao
 */

public abstract class CacheEvent extends ApplicationEvent {

    public CacheEvent(Object source) {
        super(source);
    }

}
