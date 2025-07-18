package com.idt.aiowebflux.auth;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.idt.aiowebflux.repository.RolePermissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
public class RolePermCache {

    private final RolePermissionRepository repo;
    private final RedisTemplate<String, Object> redis;

    private final AsyncCache<String, Set<String>> l2Cache;

    public RolePermCache(RolePermissionRepository repo,
                         RedisTemplate<String, Object> redis,
                         @Value("${jwt.cache.caffeine-ttl-minutes:3}") long caffeineTtl) {
        this.repo = repo;
        this.redis = redis;
        this.l2Cache = Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(caffeineTtl))
                .maximumSize(10_000)
                .buildAsync();
    }

    @SuppressWarnings("unchecked")
    public CompletableFuture<Set<String>> getPermissionsAsync(String role) {
        return l2Cache.get(role, (key, executor) -> {
            Object cached = redis.opsForValue().get(key);
            if (cached != null) {
                return CompletableFuture.completedFuture((Set<String>) cached);
            }
            return CompletableFuture.supplyAsync(() -> {
                Set<String> perms =/* repo.findPermCodesByRole(key);*/null;
                redis.opsForValue().set(key, perms, Duration.ofMinutes(15));
                return perms;
            });
        });
    }

    public Set<String> getPermissions(String role) {
        return getPermissionsAsync(role).join();
    }

    public void evict(String role) {
        l2Cache.synchronous().invalidate(role);
        redis.delete(role);
    }
}
