package com.idt.aiowebflux.auth;

import com.idt.aiowebflux.auth.mask.AclMask;
import com.idt.aiowebflux.dto.JwtPrincipal;
import com.idt.aiowebflux.repository.AclEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class GenericaAclPermissionEvaluator implements PermissionEvaluator {
    private final AclEntryRepository aclRepo;
    private final RedisTemplate<String, Object> redis;
    @Override
    public boolean hasPermission(final Authentication auth,
                                 final Object targetDomainObject,
                                 final Object permission) {

        // 예시 @PreAuthorize("hasPermission(#docId, 'READ')")
        // targetDomainObject 로 docId 가 넘어올 수 있음
        if (targetDomainObject == null)
            return false;

        /* ResourceAware 인터페이스로 ID·TYPE 추출 */
        if (targetDomainObject instanceof ResourceAware res) {
            return check(auth, String.valueOf(res.getResourceId()), res.getResourceType(), permission);
        }

        /* Document / Folder 엔티티를 직접 받았다고 가정한 fallback */
  /*      if (targetDomainObject instanceof Document d) {
            return check(auth, d.getDocId(), "DOCUMENT", permission);
        }
        if (targetDomainObject instanceof Project p) {
            return check(auth, String.valueOf(p.getProjectId()), "FOLDER", permission);
        }*/
        return false;   // 매핑 불가
    }
    // 예시 @PreAuthorize("hasPermission(#docId, 'DOCUMENT', 'READ')")
    // targetId 로 docId 가 넘어올 수 있음
    // targetType 으로 'DOCUMENT', 'FOLDER' 등 리소스 타입이 넘어올 수 있음
    // permission 으로 'READ', 'WRITE', 'DELETE' 등 권한이 넘어올 수 있음
    public boolean hasPermission(Authentication auth,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {

        if (targetId == null || targetType == null)
            return false;
        return check(auth, targetId.toString(),
                targetType.toUpperCase(), permission);
    }

    /* === 실제 권한 판단 공용 로직 ======================================= */
    private boolean check(final Authentication auth,
                          final String resourceId,
                          final String resourceType,
                          final Object permissionObj) {

        /* 0. ADMIN role 숏컷 */
        if (isAdmin(auth.getAuthorities()))
            return true;

        final String action = String.valueOf(permissionObj).toUpperCase();   // READ / WRITE / DELETE
        final String uid = ((JwtPrincipal) auth.getPrincipal()).accountId();

        /* 1. Redis 캐시 조회 (키: ACL:{uid}:{type}:{id}) */
        final String key = String.format("ACL:R:%s:%s", uid, resourceType);
        AclMask mask = (AclMask) redis.opsForValue().get(key);

        /* 2. 미스 시 DB 한 방 조회 후 캐싱 */
        if (mask == null) {
            mask = aclRepo.aggregateMask(uid, resourceType, resourceId)
                    .orElseGet(AclMask::getEmptyMask);
            redis.opsForValue().set(key, mask, Duration.ofMinutes(10));
        }

        /* 3. 플래그 비교 */
        return switch (action) {
            case "READ"   -> mask.canRead();
//            case "WRITE"  -> mask.getCanWrite();
//            case "DELETE" -> mask.getCanDelete();
            default       -> false;
        };
    }

    /* helper: ROLE_ADMIN 여부 */
    private boolean isAdmin(Collection<?> auths) {
        return auths.stream().anyMatch(a -> a.toString().equals("ROLE_ADMIN"));
    }

    /* 리소스 엔티티들이 구현해 두면 편리한 helper 인터페이스 */
    public interface ResourceAware {
        Long   getResourceId();
        String getResourceType();     // 'DOCUMENT','FOLDER','PROJECT'…
    }
}
