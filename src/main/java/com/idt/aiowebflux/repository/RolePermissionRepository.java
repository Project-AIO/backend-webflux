package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.RolePermission;
import com.idt.aiowebflux.repository.custom.CustomRolePermissionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, CustomRolePermissionRepository {
/*    @Query("SELECT rp.permission.code FROM RolePermission rp JOIN FETCH rp.permission WHERE rp.role.roleName = :key")
    Set<String> findPermCodesByRole(@Param("key") final String key);*/

    @Query("SELECT rp FROM RolePermission rp JOIN FETCH rp.permission")
    List<RolePermission> findAllByOrderByRole_RoleIdAscPermission_PermissionIdAsc();

    List<RolePermission> findByRole_RoleId(Long roleId);

    @Modifying
    void deleteByRole_RoleId(final Long roleId);

    List<RolePermission> findByRole_RoleIdIn(final List<Long> roleIds);
}
