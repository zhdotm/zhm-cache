package io.github.zhdotm.cache.core.annotation;

import io.github.zhdotm.cache.core.constant.CacheConstant;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 在不干扰方法执行的情况下更新缓存
 *
 * @author zhihao.mao
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@CachePut(cacheManager = CacheConstant.MULTI_CACHE_MANAGER)
public @interface MultiCachePut {

    @AliasFor(annotation = CachePut.class, value = "value")
    String[] value() default {};

    @AliasFor(annotation = CachePut.class, value = "cacheNames")
    String[] cacheNames() default {};

    @AliasFor(annotation = CachePut.class, value = "key")
    String key() default "";

    @AliasFor(annotation = CachePut.class, value = "keyGenerator")
    String keyGenerator() default "";

    @AliasFor(annotation = CachePut.class, value = "cacheResolver")
    String cacheResolver() default "";

    @AliasFor(annotation = CachePut.class, value = "condition")
    String condition() default "";

    @AliasFor(annotation = CachePut.class, value = "unless")
    String unless() default "";
}
