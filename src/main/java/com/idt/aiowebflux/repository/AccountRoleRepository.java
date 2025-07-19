package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.AccountRole;
import com.idt.aiowebflux.entity.composite.AccountRoleId;
import com.idt.aiowebflux.repository.custom.CustomAccountRoleRepository;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, AccountRoleId>, CustomAccountRoleRepository {

}
