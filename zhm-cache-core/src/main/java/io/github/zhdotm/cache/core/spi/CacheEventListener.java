package io.github.zhdotm.cache.core.spi;

import io.github.zhdotm.cache.core.event.CacheEvent;

/**
 * @author zhihao.mao
 */

public interface CacheEventListener {

    String getName();

    void onEvent(CacheEvent event);
}
