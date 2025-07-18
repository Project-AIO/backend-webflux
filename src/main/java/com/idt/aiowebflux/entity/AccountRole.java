package com.idt.aiowebflux.entity;

//import com.idt.aiowebflux.entity.composite.AccountRoleId;
import com.idt.aiowebflux.entity.composite.AccountRoleId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_account_role")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRole extends BaseEntity{
    @EmbeddedId
    private AccountRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "update_dt", updatable = false)
    private LocalDateTime updateDt;

    @Override
    protected void prePersistChild() {
        this.updateDt = LocalDateTime.now();
    }

    public AccountRole(final AccountRoleId id, final Account account, final Role role) {
        this.id = id;
        this.account = account;
        this.role = role;
    }
}
