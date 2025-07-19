package com.idt.aiowebflux.repository;

import static jakarta.persistence.LockModeType.PESSIMISTIC_READ;

import com.idt.aiowebflux.entity.DocumentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentFileRepository extends JpaRepository<DocumentFile, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            INSERT INTO tb_doc_file
                    (doc_id, extension, path, file_name,
                     total_page, file_size, revision)
            SELECT  :docId,
                    :extension,
                    :path,
                    :fileName,
                    :totalPage,
                    :fileSize,
                    /* ---------- 핵심 ---------- */
                    IFNULL(
                        ROUND(         -- ① 소수 첫째 자리에서 반올림
                            (SELECT MAX(df.revision)
                               FROM tb_doc_file df
                              WHERE df.file_name = :fileName
                              FOR UPDATE
                            ) + 0.1,
                            1           -- ← 소수 1자리
                        ),
                        1.0             -- ② 처음 들어올 땐 1.0
                    ) AS next_revision;
            """,
            nativeQuery = true)
    int insertWithAutoRevision(@Param("docId") final String docId,      // FK
                               @Param("extension") final String extension,
                               @Param("path") final String path,
                               @Param("fileName") final String fileName,
                               @Param("totalPage") final Integer totalPage,
                               @Param("fileSize") final Long fileSize);

    @Lock(PESSIMISTIC_READ)
    @Query("""
            SELECT df.revision
              FROM DocumentFile df
             WHERE df.document.docId = :docId
               AND df.fileName = :fileName
            """)
    String findRevisionByDocument_DocIdAndFileName(@Param("docId") final String docId,
                                                   @Param("fileName") final String fileName);
}
