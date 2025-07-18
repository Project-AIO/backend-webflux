package com.idt.aiowebflux.entity;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false, length = 64, unique = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public void updateName(final String name) {
        if (name == null || name.isBlank()) {
            throw DomainExceptionCode.ROLE_NAME_INVALID.newInstance("수정할 역할명이 없습니다.");
        }
        this.name = name;
    }
}
