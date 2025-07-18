package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.KnowledgeAccountPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomKnowledgeAccountRepository {

    Page<KnowledgeAccountPageDto> findProjectAccountDataPageByProjectId(final Integer projectId, final Pageable pageable);

    void deleteAllByProject_ProjectIdAndAccount_AccountIdIn(final Integer projectId, final List<String> accountIds);

//    List<ProjectAccount> findByProject_ProjectId(final Integer projectId);
}
