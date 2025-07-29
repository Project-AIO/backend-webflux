package com.idt.aiowebflux.service;

import com.idt.aiowebflux.dto.DocumentRevisionDto;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.idt.aiowebflux.entity.constant.AccessModifier;
import com.idt.aiowebflux.validator.ReactiveFileValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class DocumentPersistenceService {

    private final DocumentFileService documentFileService;
    private final ReactiveFileValidator reactiveFileValidator;


    @Transactional
    public Mono<Void> executePostProcess(Path filePath, String fileName, Long folderId, String accountId,
                                         AccessModifier accessModifier, String uploadId, Long totalBytes) {
        return Mono.fromCallable(() -> persistUploadedSession(filePath, fileName, folderId, accountId, accessModifier))
                .subscribeOn(Schedulers.boundedElastic())
                .delayUntil(path ->
                reactiveFileValidator.validate(fileName, totalBytes, uploadId, path)
                )
                .then();
    }


    public Path persistUploadedSession(Path filePath, String fileName, Long folderId, String accountId,
                                       AccessModifier accessModifier) throws IOException {
        final String extension = org.apache.commons.io.FilenameUtils.getExtension(fileName).toLowerCase();
        final Integer totalPage = extract(filePath, extension);
        final DocumentRevisionDto dto = documentFileService.saveDocumentData(
                fileName,
                extension,
                folderId,
                accountId,
                accessModifier,
                Files.size(filePath),
                totalPage
        );

        final String name = dto.docId() + "_" + fileName;
        Path path = filePath.resolveSibling(name);
        Files.move(filePath, path, StandardCopyOption.REPLACE_EXISTING);

        return path;
    }

    public Integer extract(Path path, String ext) {
        try {
            return switch (ext) {
                case "pdf" -> countPdf(path);
                case "docx" -> countDocx(path);
                case "doc" -> countDoc(path);
                case "ppt", "pptx" -> countPpt(path);
                default -> -1; // unknown
            };
        } catch (IOException e) {
            throw new IllegalStateException("파일을 읽는 중 오류가 발생했습니다 -> " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalStateException("페이지 수를 가져오는 중 오류가 발생했습니다 -> " + e.getMessage(), e);
        }
    }

    private int countPdf(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            try (RandomAccessRead rar = new RandomAccessReadBuffer(is);
                 PDDocument doc = Loader.loadPDF(rar)) {           // 스트림 래핑
                return doc.getNumberOfPages();
            } catch (IOException e) {
                throw new IllegalStateException("getTotalPages -> PDF 파일 처리 중 오류가 발생했습니다.", e);
            }
        }
    }

    private int countDocx(Path path) throws Exception {
        try (OPCPackage pkg = OPCPackage.open(path.toFile());
             XWPFDocument doc = new XWPFDocument(pkg)) {

            return doc.getProperties()
                    .getExtendedProperties()
                    .getUnderlyingProperties()
                    .getPages();
        }
    }

    public int countDoc(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            try (HWPFDocument doc = new HWPFDocument(is)) {
                return doc.getSummaryInformation().getPageCount();
            }
        }
    }

    private int countPpt(Path path) throws Exception {
        // PPTX
        try (XMLSlideShow ppt = new XMLSlideShow(Files.newInputStream(path))) {
            return ppt.getSlides().size();
        }
    }

}
