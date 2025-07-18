package com.idt.aiowebflux.dto;


import com.idt.aiowebflux.entity.Role;
import com.querydsl.core.annotations.QueryProjection;

public record RoleDto(
        Long roleId,
        String roleName
) {
    @QueryProjection
    public RoleDto {
    }
    public static RoleDto from(final Role roles) {
        return new RoleDto(
                roles.getRoleId(),
                roles.getName()
        );
    }
}
