package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.DocFileDto;
import com.idt.aiowebflux.dto.DocumentRevisionDto;
import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.entity.DocumentFile;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.DocumentFileRepository;
import com.idt.aiowebflux.util.FileUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class DocumentFileService {
    private final DocumentFileRepository documentFileRepository;
    private final DocumentService documentService;

    @Transactional(readOnly = true)
    public DocFileDto getDocumentFilePathAndName(final String docId) {
        final DocumentFile documentFile = documentFileRepository.findDocumentFileByDocument_DocId(docId)
                .orElseThrow(DomainExceptionCode.DOCUMENT_FILE_NOT_FOUND::newInstance);

        //실질적으로 파일은 docId로 저장되기 때문에 docId와 확장자를 합침
        final String filePath = documentFile.getPath() + File.separator + documentFile.getDocument().getDocId() + "." + documentFile.getExtension();
        // 확장자를 제외한 파일 이름을 가져오기 위해서
//        final String fileName = documentFile.getOriginalFileName();
        final String fileName = "임시"; // TODO. 파일이름 임시처리
        return new DocFileDto(filePath, fileName);
    }

    @Transactional
    public DocumentRevisionDto saveDocumentData(
            final String originalName,
            final String extension,
            final Long folderId,
            final String accountId,
            final AccessLevel accessLevel,
            final long fileBytesLong,
            final Integer totalPage
    ){

        final Document document = documentService.createDocument(folderId, accountId, accessLevel);
        final String absoluteFilePath = Paths.get(FileUtils.ROOT_PATH+ File.separator+FileUtils.DOC_ROOT, document.getDocId() + "." + extension).toString();

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

        final String revision = documentFileRepository.findRevisionByDocument_DocIdAndFileName(document.getDocId(), originalName);

        return new DocumentRevisionDto(document.getDocId(), revision);

    }
}
