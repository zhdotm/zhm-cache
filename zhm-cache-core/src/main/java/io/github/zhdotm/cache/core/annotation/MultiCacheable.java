package io.github.zhdotm.cache.core.annotation;

import io.github.zhdotm.cache.core.constant.CacheConstant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 触发缓存填充
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Cacheable(cacheManager = CacheConstant.MULTI_CACHE_MANAGER)
public @interface MultiCacheable {

    @AliasFor(annotation = Cacheable.class, value = "value")
    String[] value() default {};

    @AliasFor(annotation = Cacheable.class, value = "cacheNames")
    String[] cacheNames() default {};

    @AliasFor(annotation = Cacheable.class, value = "key")
    String key() default "";

    @AliasFor(annotation = Cacheable.class, value = "keyGenerator")
    String keyGenerator() default "";

    @AliasFor(annotation = Cacheable.class, value = "cacheResolver")
    String cacheResolver() default "";

    @AliasFor(annotation = Cacheable.class, value = "condition")
    String condition() default "";

    @AliasFor(annotation = Cacheable.class, value = "unless")
    String unless() default "";

    @AliasFor(annotation = Cacheable.class, value = "sync")
    boolean sync() default false;
}
