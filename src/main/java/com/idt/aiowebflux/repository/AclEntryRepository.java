package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.auth.mask.AclMask;
import com.idt.aiowebflux.entity.AclEntry;
import com.idt.aiowebflux.entity.constant.PrincipalType;
import com.idt.aiowebflux.entity.constant.ResourceType;
import com.idt.aiowebflux.repository.custom.CustomAclRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AclEntryRepository extends JpaRepository<AclEntry, Long>, CustomAclRepository {

    @Query(value = """
            WITH roles AS (SELECT role_id FROM user_role WHERE user_id = :uid)
            SELECT COUNT(DISTINCT resource_id)
            FROM   acl_entry
            WHERE  resource_type = 'DOCUMENT'
              AND  resource_id IN (:ids)
              AND  can_read = 1
              AND  principal_type = 'ROLE' AND principal_id IN (SELECT role_id FROM roles)
              )
            """, nativeQuery = true)
    int countReadable(final @Param("uid") String uid, final @Param("ids") Collection<Long> ids);

    @Query(value = """
            WITH roles AS (SELECT role_id FROM user_role WHERE user_id = :uid)
            SELECT MAX(can_read)   AS canRead /*,
                   MAX(can_write)  AS canWrite,
                   MAX(can_delete) AS canDelete*/
            FROM acl_entry
            WHERE resource_type=:type AND resource_id=:id
              AND ((principal_type='USER' AND principal_id=:uid)
                OR  (principal_type='ROLE' AND principal_id IN (SELECT role_id FROM roles)))
            """, nativeQuery = true)
    Optional<AclMask> aggregateMask(@Param("uid") final String uid, @Param("type") final String type, @Param("id") final String id);

    @Query("""
            SELECT a FROM AclEntry a 
                     WHERE a.id.principalId = :principalId
                          AND a.id.principalType = :principalType
                          AND a.id.resourceType = :resourceType
            """)
    List<AclEntry> findByPrincipalAndResourceType(
            @Param("principalId") final Long principalId,
            @Param("principalType") final PrincipalType principalType,
            @Param("resourceType") final ResourceType resourceType
    );

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        delete from AclEntry e
        where  e.id.principalType = :pt
           and e.id.principalId   = :pid
           and e.id.resourceType  = :type
    """)
    void deleteAclEntries(@Param("pt") final PrincipalType pt,
                                @Param("pid") final String pid,
                                @Param("type") final ResourceType type);

    @Modifying
    @Query("""
        delete from AclEntry e
        where  e.id.principalType = :principalType
           and e.id.principalId   in :roleIds
    """)
    void deleteByPrincipalIdIn(@Param("principalType") final PrincipalType principalType, @Param("roleIds") final List<Long> roleIds);
}
