package com.idt.aiowebflux.entity;

import com.idt.aiowebflux.entity.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_account")
public class Account extends BaseEntity{

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "update_dt", updatable = false)
    private LocalDateTime updateDt;

    public void updateName(final String name) {
        this.name = name;
    }

    public Account(final String accountId, final String name, final String pw) {
        this.accountId = accountId;
        this.name = name;
        this.pw = pw;
        this.status = Status.ACTIVE; // Default status
    }
    public void updateStatus(final Status status) {
        this.status = status;
    }

    @Override
    protected void prePersistChild() {
        this.updateDt = LocalDateTime.now();
    }



}
