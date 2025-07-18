package com.idt.aiowebflux.auth;

import com.idt.aiowebflux.dto.JwtPrincipal;
import com.idt.aiowebflux.repository.AclEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

@Component("acl")
@RequiredArgsConstructor
public class AclSpelHelper {
    private final AclEntryRepository repo;
    private final RedisTemplate<String, Object> redis;

    public boolean allReadable(final Collection<Long> ids) {

        final Authentication au = SecurityContextHolder.getContext().getAuthentication();
        final String uid = ((JwtPrincipal) au.getPrincipal()).accountId();
        if (au.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) return true;

        final String sorted = ids.stream().sorted().map(String::valueOf).collect(Collectors.joining(",`"));
        final String cacheKey = "ACL:R:%s:%s";
        final String key = cacheKey.formatted(uid, sorted);

        final Boolean hit = (Boolean) redis.opsForValue().get(key);
        if (hit != null) return hit;

        final boolean allowed = repo.countReadable(uid, ids) == ids.size();
        redis.opsForValue().set(key, allowed, Duration.ofMinutes(10));
        return allowed;
    }

    /* 권한 변경 서비스에서 호출 */
    public void evictUser(Long uid) {
        redis.delete(redis.keys("ACL:R:" + uid + ":*"));
    }
}
