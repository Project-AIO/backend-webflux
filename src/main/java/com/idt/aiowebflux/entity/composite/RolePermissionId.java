package com.idt.aiowebflux.entity.composite;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class RolePermissionId implements Serializable {
    @Serial
    private static final long serialVersionUID = 793L;

    private Long roleId;
    private Long permissionId;
}
