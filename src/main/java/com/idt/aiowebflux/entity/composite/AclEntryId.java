package com.idt.aiowebflux.entity.composite;

import com.idt.aiowebflux.entity.constant.PrincipalType;
import com.idt.aiowebflux.entity.constant.ResourceType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class AclEntryId {
    @Enumerated(EnumType.STRING)
    @Column(name = "principal_type", length = 30, nullable = false)
    private PrincipalType principalType;

    @Column(name = "principal_id", nullable = false)
    private Long principalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", length = 30, nullable = false)
    private ResourceType resourceType;

    @Column(name = "resource_id", nullable = false)
    private Long resourceId;
}
