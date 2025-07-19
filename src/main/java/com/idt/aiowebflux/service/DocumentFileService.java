package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.DocumentRevisionDto;
import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.repository.DocumentFileRepository;
import com.idt.aiowebflux.util.FileUtils;
import java.io.File;
import java.nio.file.Paths;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DocumentFileService {
    private final DocumentFileRepository documentFileRepository;
    private final DocumentService documentService;


    @Transactional
    public DocumentRevisionDto saveDocumentData(
            final String originalName,
            final String extension,
            final Long folderId,
            final String accountId,
            final AccessLevel accessLevel,
            final long fileBytesLong,
            final Integer totalPage
    ) {

        final Document document = documentService.createDocument(folderId, accountId, accessLevel);
        final String absoluteFilePath = Paths.get(FileUtils.ROOT_PATH + File.separator + FileUtils.DOC_ROOT,
                document.getDocId() + "." + extension).toString();

        /**
         * 동시성 문제를 방지하기 위해서 native 쿼리로 작성
         * UNIQUE + Pessimistic Lock + 재시도 패턴으로 revision 충돌 없이 안전하게 운용
         */
        documentFileRepository.insertWithAutoRevision(
                document.getDocId(),
                extension,
                absoluteFilePath,
                originalName,
                totalPage,
                fileBytesLong);

        final String revision = documentFileRepository.findRevisionByDocument_DocIdAndFileName(document.getDocId(),
                originalName);

        return new DocumentRevisionDto(document.getDocId(), revision);

    }
}
