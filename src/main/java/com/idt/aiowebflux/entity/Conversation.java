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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_conversation")
public class Conversation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id")
    private Long conversationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "knowledge_id")
    private Knowledge knowledge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "title")
    private String title;

    @Column(name = "used_dt", updatable = false)
    private LocalDateTime usedDt;

    @Override
    protected void prePersistChild() {
        this.usedDt = LocalDateTime.now();
    }


    public Conversation(final Account account, final Knowledge knowledge, final String title) {
        this.account = account;
        this.knowledge = knowledge;
        this.title = title;
    }

    public void updateTitle(String resultTitle) {
        this.title = resultTitle;
    }
}
