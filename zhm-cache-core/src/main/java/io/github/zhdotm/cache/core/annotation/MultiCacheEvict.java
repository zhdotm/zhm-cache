package io.github.zhdotm.cache.core.annotation;

import io.github.zhdotm.cache.core.constant.CacheConstant;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 触发缓存逐出
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@CacheEvict(cacheManager = CacheConstant.MULTI_CACHE_MANAGER)
public @interface MultiCacheEvict {

    @AliasFor(annotation = CacheEvict.class, value = "value")
    String[] value() default {};

    @AliasFor(annotation = CacheEvict.class, value = "cacheNames")
    String[] cacheNames() default {};

    @AliasFor(annotation = CacheEvict.class, value = "key")
    String key() default "";

    @AliasFor(annotation = CacheEvict.class, value = "keyGenerator")
    String keyGenerator() default "";

    @AliasFor(annotation = CacheEvict.class, value = "cacheResolver")
    String cacheResolver() default "";

    @AliasFor(annotation = CacheEvict.class, value = "condition")
    String condition() default "";

    @AliasFor(annotation = CacheEvict.class, value = "allEntries")
    boolean allEntries() default false;

    @AliasFor(annotation = CacheEvict.class, value = "beforeInvocation")
    boolean beforeInvocation() default false;
}
