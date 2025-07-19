package com.idt.aiowebflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_knowledge")
public class Knowledge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "knowledge_id")
    private Long knowledgeId;


    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "update_dt", updatable = false)
    private LocalDateTime updateDt;

    public Knowledge(final String name, final Account account, final String description) {
        this.name = name;
        this.account = account;
        this.description = description;
    }

    @Override
    protected void prePersistChild() {
        this.updateDt = LocalDateTime.now();
    }

}
