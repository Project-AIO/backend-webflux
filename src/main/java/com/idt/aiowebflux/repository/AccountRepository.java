package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Account;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    List<Account> findAll();

    Page<Account> findAll(Pageable pageable);


}
