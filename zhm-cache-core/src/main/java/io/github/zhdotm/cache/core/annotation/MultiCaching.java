package io.github.zhdotm.cache.core.annotation;

import org.springframework.cache.annotation.Caching;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 重新组合要应用于方法的多个缓存操作
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Caching
public @interface MultiCaching {

    @AliasFor(annotation = Caching.class, value = "cacheable")
    MultiCacheable[] cacheable() default {};

    @AliasFor(annotation = Caching.class, value = "put")
    MultiCachePut[] put() default {};

    @AliasFor(annotation = Caching.class, value = "evict")
    MultiCacheEvict[] evict() default {};
}
