package io.github.zhdotm.cache.example.config;

import io.github.zhdotm.cache.core.manager.MultiCacheManager;
import io.github.zhdotm.cache.core.manager.SortedCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

/**
 * @author zhihao.mao
 */

@Configuration
public class CacheConfig {

    @Bean

    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        return template;

    }


    @Bean
    public RedisCacheWriter redisCacheWriter(RedisTemplate<String, Object> redisTemplate) {

        return RedisCacheWriter.nonLockingRedisCacheWriter(Objects.requireNonNull(redisTemplate.getConnectionFactory()));

    }

    @Bean
    public CacheManager multiCacheManager(RedisCacheWriter redisCacheWriter) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();

        RedisCacheManager redisCacheManager = RedisCacheManager.RedisCacheManagerBuilder.fromCacheWriter(redisCacheWriter).build();

        return new MultiCacheManager(new SortedCacheManager(1, "caffeineCacheManager", caffeineCacheManager),
                new SortedCacheManager(2, "redisCacheManager", redisCacheManager));
    }

}
