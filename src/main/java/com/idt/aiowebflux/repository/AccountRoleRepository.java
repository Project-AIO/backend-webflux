package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.AccountRole;
import com.idt.aiowebflux.entity.composite.AccountRoleId;
import com.idt.aiowebflux.repository.custom.CustomAccountRoleRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, AccountRoleId>, CustomAccountRoleRepository {

    List<AccountRole> findByAccount_AccountId(@NotNull final String accountId);

    List<AccountRole> findByAccount_AccountIdIn(@NotNull final List<String> accountIds);

    @Query("SELECT r.name FROM AccountRole ar JOIN ar.role r WHERE ar.account.accountId = :accountId")
    Set<String> findRoleNameByAccount_AccountId(@NotNull final String accountId);


    @Modifying
    void deleteByAccount_AccountId(final String accountId);

    @Modifying
    void deleteByAccount_AccountIdIn(final List<String> accountIds);

    void deleteByRole_RoleIdIn(final List<Long> roleIds);
}
