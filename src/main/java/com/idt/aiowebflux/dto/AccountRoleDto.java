package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.AccountRole;
import com.idt.aiowebflux.entity.constant.Status;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;
import java.util.List;

public record AccountRoleDto(
        String accountId,
        String name,
        Status status,
        List<RoleDto> roles,
        LocalDateTime updateDt
) {
    @QueryProjection
    public AccountRoleDto {
    }

    public static AccountRoleDto from(final List<AccountRole> accountRoles) {
        final List<RoleDto> roleDtos = accountRoles.stream()
                .map(AccountRole::getRole)
                .map(RoleDto::from)
                .toList();
        return new AccountRoleDto(
                accountRoles.get(0).getAccount().getAccountId(),
                accountRoles.get(0).getAccount().getName(),
                accountRoles.get(0).getAccount().getStatus(),
                roleDtos,
                accountRoles.get(0).getUpdateDt()
        );
    }
}
