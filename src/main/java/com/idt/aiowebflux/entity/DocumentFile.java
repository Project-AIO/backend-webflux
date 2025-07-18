package com.idt.aiowebflux.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_doc_file",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_file_revision",                // 생성될 인덱스 이름
                columnNames = {"file_name", "revision"}   // 컬럼 순서 주의
        )
)
public class DocumentFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doc_file_id")
    private Long docFileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id")
    private Document document;
    @Column(name = "extension", nullable = false, length = 10)
    private String extension;
    @Column(name = "path", nullable = false, length = 255)
    private String path;

    @Column(name = "file_name", nullable = false, length = 50)
    private String fileName;

    @Column(name = "total_page", nullable = false)
    private int totalPage;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "revision")
    private String revision;

    public DocumentFile(Document document, String extension, String path, String fileName,
                        int totalPage, long fileSize, String revision) {
        this.document = document;
        this.extension = extension;
        this.path = path;
        this.fileName = fileName;
        this.totalPage = totalPage;
        this.fileSize = fileSize;
        this.revision = revision;
    }

}

