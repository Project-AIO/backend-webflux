package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.DocAccountDataResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomDocumentAccountRepository {
    Page<DocAccountDataResponse> findDocAccountDataPageByDocId(final String docId, final Pageable pageable, final Long projectId);

    void deleteAllByDocument_DocIdAndAccount_AccountIds(final String docId, final List<String> accountIds);

}
