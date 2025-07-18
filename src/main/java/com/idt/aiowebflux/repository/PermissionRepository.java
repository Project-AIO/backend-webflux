package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {


    List<Permission> findByPermissionIdIn(final List<Long> permissionIds);


    List<Permission> findAllByOrderByDisplayNameAsc();
}
