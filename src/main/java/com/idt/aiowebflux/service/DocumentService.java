package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.DocFileDto;
import com.idt.aiowebflux.dto.DocumentInfomationDto;
import com.idt.aiowebflux.dto.DocumentPathDto;
import com.idt.aiowebflux.dto.DocumentWrapper;
import com.idt.aiowebflux.dto.FileDto;
import com.idt.aiowebflux.dto.KnowledgeFolderReferenceDto;
import com.idt.aiowebflux.dto.ModelDataDto;
import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.entity.DocumentFile;
import com.idt.aiowebflux.entity.Folder;
import com.idt.aiowebflux.entity.constant.State;
import com.idt.aiowebflux.exception.DomainExceptionCode;

import com.idt.aiowebflux.repository.AccountRepository;
import com.idt.aiowebflux.repository.DocumentFileRepository;
import com.idt.aiowebflux.repository.DocumentRepository;
import com.idt.aiowebflux.repository.FolderRepository;
import com.idt.aiowebflux.repository.KnowledgeRepository;
import com.idt.aiowebflux.request.CoreDocDeleteRequest;
import com.idt.aiowebflux.request.FileDownloadResult;
import com.idt.aiowebflux.request.core.CoreDataRequest;
import com.idt.aiowebflux.request.core.CoreErrorRequest;
import com.idt.aiowebflux.response.ContentResponse;
import com.idt.aiowebflux.response.DocStateResponse;
import com.idt.aiowebflux.response.DocumentDataResponse;
import com.idt.aiowebflux.response.DocumentDatasetResponse;
import com.idt.aiowebflux.util.FileUtils;
import com.idt.aiowebflux.util.SseSubscribeUuidUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final FileUtils fileUtils;
    private final KnowledgeRepository knowledgeRepository;
    private final FolderRepository folderRepository;
    private final DocumentFileRepository documentFileRepository;

    private final SseSubscribeUuidUtil subscribeUuidUtil;
    private final ApplicationEventPublisher eventPublisher;
    private final AccountRepository accountRepository;

    @Value("${spring.profiles.default}")
    private String defaultProfile;


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
