package com.idt.aiowebflux.entity.composite;

import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
