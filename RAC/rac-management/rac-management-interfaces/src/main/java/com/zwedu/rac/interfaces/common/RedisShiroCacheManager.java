package com.zwedu.rac.interfaces.common;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis session 缓存实现
 *
 * @author qingchuan
 * @date 2021/1/9
 */
public class RedisShiroCacheManager implements CacheManager {
    /**
     * redis 操作模板
     */
    private RedisTemplate redisTemplate;

    /**
     * 无参构造
     */
    public RedisShiroCacheManager() {
    }

    /**
     * constructor
     *
     * @param redisTemplate redis操作模板类
     */
    public RedisShiroCacheManager(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {
        return new RedisShiroCache<K, V>(redisTemplate, cacheName);
    }
}
