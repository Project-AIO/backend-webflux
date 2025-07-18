package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.Role;
import com.querydsl.core.annotations.QueryProjection;


public record AccountData(
        String accountId,
        String adminId,
        Role role,
        String name
) {
    @QueryProjection
    public AccountData {

    }
}
