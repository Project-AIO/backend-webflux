package com.idt.aiowebflux.service;

import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.entity.Folder;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.AccountRepository;
import com.idt.aiowebflux.repository.DocumentRepository;
import com.idt.aiowebflux.repository.FolderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public Document createDocument(final Long folderId, final String accountId, final AccessLevel accessLevel) {

        final boolean folderExists = folderRepository.existsById(folderId);
        if (!folderExists) {
            throw new RuntimeException("프로젝트 폴더가 존재하지 않습니다.");
        }

        final Account account = accountRepository.findById(accountId)
                .orElseThrow(DomainExceptionCode.ACCOUNT_NOT_FOUND::newInstance);

        final Folder referenceById = folderRepository.getReferenceById(folderId);

        final Document document = new Document(referenceById, State.AVAILABLE, 0, accessLevel, account);
        return documentRepository.save(document);
    }

}
