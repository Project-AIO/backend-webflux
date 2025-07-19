package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.AccountSecret;

public interface CustomAccountRoleRepository {


    AccountSecret findAccountSecretById(final String accountId);
}
