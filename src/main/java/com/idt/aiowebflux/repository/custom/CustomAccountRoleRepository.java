package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.AccountRoleDto;
import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.dto.SearchParam;
import com.idt.aiowebflux.entity.constant.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAccountRoleRepository {
    Page<AccountRoleDto> findAllByPage(final Pageable pageable,
                                       final String searchField,
                                       final SearchParam searchParam,
                                       final Status status);

    AccountSecret findAccountSecretById(final String accountId);
}
