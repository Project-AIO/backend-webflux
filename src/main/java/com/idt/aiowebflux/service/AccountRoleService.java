package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.repository.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;


    @Transactional(readOnly = true)
    public AccountSecret findAccountRoleById(final String accountId) {
        return accountRoleRepository.findAccountSecretById(accountId);
    }

}
