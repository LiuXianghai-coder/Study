package com.zwedu.rac.interfaces.common;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collection;
import java.util.Set;

/**
 * 缓存实现
 *
 * @author qingchuan
 * @date 2021/1/9
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {
    /**
     * redis 操作类
     */
    private RedisTemplate<String, V> redisTemplate;
    /**
     * 缓存名称
     */
    private String cacheName;

    public RedisShiroCache() {

    }

    public RedisShiroCache(RedisTemplate<String, V> redisTemplate, String cacheName) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
        return (V) redisTemplate.opsForHash().get(cacheName, k.toString());
    }

    @Override
    public V put(K k, V v) throws CacheException {
        redisTemplate.opsForHash().put(cacheName, k.toString(), v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        return (V) redisTemplate.opsForHash().delete(cacheName, k.toString());
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(cacheName);
    }

    @Override
    public int size() {
        return redisTemplate.opsForHash().size(cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return (Set<K>) redisTemplate.opsForHash().keys(cacheName);
    }

    @Override
    public Collection<V> values() {
        return (Collection<V>) redisTemplate.opsForHash().values(cacheName);
    }
}
