package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAll();

    Page<Account> findAll(Pageable pageable);

    @Modifying
    @Query(value = "insert into tb_account (account_id, admin_id, name, role) values (:accountId, :adminId, :name, :role)", nativeQuery = true)
    void saveAccount(@Param("accountId") final String accountId, @Param("accountId") final String adminId,
                     @Param("roleName") final String name, @Param("role") final String role);

    @Modifying
    @Query(value = "select a.account_id, a.admin_id, a.role, a.name, u.license_key from tb_account a " +
            "left outer join tb_admin u on a.admin_id = u.admin_id", nativeQuery = true)
    List<Account> findByAccounts();

    // 상태가 'DEACTIVATED'이고 updatedAt가 30일 이후인 계정들을 조회
    @Query("""
       select a
         from Account a
        where a.status    = com.idt.aiowebflux.entity.constant.Status.INACTIVE
          and a.updateDt < :threshold
       """)
    List<Account> findToBeDeletedAccounts(@Param("threshold") LocalDateTime threshold);

    @Modifying
    @Query("update Account a set a.status = com.idt.aiowebflux.entity.constant.Status.DELETED where a.accountId in :accountIds")
    int markAsDeleted(@Param("accountIds") final List<String> accountIds);

    @Query("select a.name from Account a where a.accountId = :accountId")
    String findNameByAccountId(@Param("accountId") final String accountId);
}
