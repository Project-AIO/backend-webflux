package com.idt.aiowebflux.entity;


import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.entity.generator.Sha256IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_doc")
public class Document {

    @Id
    @Column(name = "doc_id")
    private String docId;

    @JoinColumn(name = "folder_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Folder folder;

    @JoinColumn(name = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    @Column(name = "progress")
    private Integer progress;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_modifier")
    private AccessLevel accessModifier;

    @Column(name = "upload_dt")
    private LocalDateTime uploadDt;

    public Document(String docId, Folder folder, Account account, Integer progress, LocalDateTime uploadDt) {
        this.docId = docId;
        this.folder = folder;
        this.account = account;
        this.progress = progress;
        this.uploadDt = uploadDt;
        this.state = State.PENDING;
    }

    public Document(final Folder folder, final State state, final Integer progress, final AccessLevel accessLevel,
                    final Account account) {
        this.folder = folder;
        this.state = state;
        this.progress = progress;
        this.accessModifier = accessLevel;
        this.account = account;
    }

    public void markAsReady() {
        this.state = State.READY;
    }

    public void updateState(final State state) {
        this.state = state;
    }

    @PrePersist
    private void prePersist() {
        // docId가 null인 경우에만 생성하도록 처리합니다.
        if (this.docId == null) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                // 예: UUID를 생성하여 SHA-256 해시로 변환
                String randomValue = UUID.randomUUID().toString();
                byte[] hashBytes = md.digest(randomValue.getBytes(StandardCharsets.UTF_8));
                this.docId = Sha256IdGenerator.bytesToHex(hashBytes);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("SHA-256 알고리즘을 사용할 수 없습니다.", e);
            }
        }
        this.uploadDt = LocalDateTime.now();
    }


    public void updateProgress(final Integer progress, final State state) {
        this.progress = progress;
        this.state = state;
    }


}
