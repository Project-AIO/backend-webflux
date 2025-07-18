package com.idt.aiowebflux.entity;

import com.idt.aiowebflux.entity.composite.AclEntryId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`tb_acl_entry`")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AclEntry {
    @EmbeddedId
    private AclEntryId id;

    @Column(name = "perm_mask", nullable = false)
    private Integer permMask;

}
