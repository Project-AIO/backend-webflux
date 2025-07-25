package com.idt.aiowebflux.service;

import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.entity.Folder;
import com.idt.aiowebflux.entity.constant.AccessModifier;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final FolderRepository folderRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public Document createDocument(final Long folderId, final String accountId, final AccessModifier accessModifier) {

        final boolean folderExists = folderRepository.existsById(folderId);
        if (!folderExists) {
            throw new RuntimeException("프로젝트 폴더가 존재하지 않습니다.");
        }

        final Account account = accountRepository.findById(accountId)
                .orElseThrow(DomainExceptionCode.ACCOUNT_NOT_FOUND::newInstance);

        final Folder referenceById = folderRepository.getReferenceById(folderId);

        final Document document = new Document(referenceById, State.AVAILABLE, 100, accessModifier, account);
        return documentRepository.save(document);
    }

    public int deleteDocumentFiles(final List<String> pathList) {
        int deletedCount =0 ;
        for (String pl : pathList) {
            Path path = Path.of(pl);
            try {
                if (Files.deleteIfExists(path)) {
                    log.info("Deleted file: {}", path);
                    deletedCount++;
                } else {
                    log.warn("File does not exist: {}", path);
                }
            } catch (IOException e) {
                log.error("Failed to delete file: {}", path, e);
                // 필요 시 예외를 다시 던져 트랜잭션 롤백 유발 가능
                 throw new RuntimeException("File deletion failed: " + path, e);
            }
        }
        return deletedCount;
    }

    public void updateDocumentName(final String id, final String newName, final String path) {
        final Path source = Path.of(path);
        final Path target = source.resolveSibling(id+"_"+newName);

        try {
            Files.move(source,               // 원본
                    target,               // 대상
                    StandardCopyOption.REPLACE_EXISTING,   // 있으면 덮어쓰기
                    StandardCopyOption.ATOMIC_MOVE);       // 가능하면 원자적 이동
        } catch (IOException e) {
            throw new RuntimeException("파일 이름 변경 중 오류 발생: " + e.getMessage(), e);
        }
    }
}
