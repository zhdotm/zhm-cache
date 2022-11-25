package io.github.zhdotm.cache.core.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;

import java.util.concurrent.Callable;

/**
 * 可排序缓存
 *
 * @author zhihao.mao
 */

@Slf4j
@AllArgsConstructor
public class SortedCache implements Cache {

    @Getter
    private final Integer sort;

    @Getter
    private final Cache cache;

    @Override
    public String getName() {

        return cache.getName();
    }

    @Override
    public Object getNativeCache() {

        return cache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {

        return cache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {

        return cache.get(key, type);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {

        return cache.get(key, valueLoader);
    }

    @Override
    public void put(Object key, Object value) {

        cache.put(key, value);
    }

    @Override
    public void evict(Object key) {

        cache.evict(key);
    }

    @Override
    public void clear() {

        cache.clear();
    }
    
}
