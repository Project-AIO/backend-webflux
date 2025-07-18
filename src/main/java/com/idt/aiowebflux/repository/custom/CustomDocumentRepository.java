package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.DocumentDataResponse;
import com.idt.aiowebflux.response.DocumentDatasetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomDocumentRepository {
    List<DocumentDataResponse> findDocumentDataByFolderId(final Long folderId);

    Optional<DocumentDataResponse> findDocumentDataById(final String docId);

    Page<DocumentDatasetResponse> findDocumentDatasetByFolderId(final Long knowledgeId, final String searchField,
                                                                final String searchInput, final Pageable pageable);

}
