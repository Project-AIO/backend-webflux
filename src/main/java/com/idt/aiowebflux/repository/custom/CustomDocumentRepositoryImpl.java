package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.entity.Document;
import com.idt.aiowebflux.repository.custom.condition.DocumentSearchField;
import com.idt.aiowebflux.response.DocumentDataResponse;
import com.idt.aiowebflux.response.DocumentDatasetResponse;
import com.idt.aiowebflux.response.QDocumentDataResponse;
import com.idt.aiowebflux.response.QDocumentDatasetResponse;
import com.idt.aiowebflux.response.QDocumentFileData;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QDocument.document;
import static com.idt.aiowebflux.entity.QDocumentFile.documentFile;
import static com.idt.aiowebflux.entity.QFolder.folder;

@Repository

public class CustomDocumentRepositoryImpl extends QuerydslRepositorySupport implements CustomDocumentRepository {



  /*   @Deprecated
    private static final Map<String, Function<String, BooleanExpression>> SEARCH_CONDITIONS = new HashMap<>();

   static {
        SEARCH_CONDITIONS.put(DocumentFile.SOURCE_NAME.getValue(), input -> documentFile.path.like("%" + input + "%"));
        SEARCH_CONDITIONS.put(DocumentFile.FILE_NAME.getValue(),
                input -> documentFile.fileName.like("%" + input + "%"));
        SEARCH_CONDITIONS.put(DocumentFile.TOTAL_PAGES.getValue(),
                input -> documentFile.totalPage.like("%" + input + "%"));
        SEARCH_CONDITIONS.put(DocumentFile.FILE_SIZE.getValue(),
                input -> documentFile.fileSize.like("%" + input + "%"));
        SEARCH_CONDITIONS.put(DocumentFile.STATE.getValue(), input -> document.state.stringValue().contains(input));
        SEARCH_CONDITIONS.put(Document.UPLOAD_DATE.getValue(),
                input -> document.uploadDt.stringValue().contains(input));
    }*/

    private final JPAQueryFactory queryFactory;

    public CustomDocumentRepositoryImpl(final JPAQueryFactory queryFactory) {
        super(Document.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<DocumentDataResponse> findDocumentDataByFolderId(final Long folderId) {

        return queryFactory
                .select(new QDocumentDataResponse(
                        document.docId,
                        folder.folderId,
                        document.state,
                        document.progress,
                        new QDocumentFileData(
                                documentFile.docFileId,
                                documentFile.extension,
                                documentFile.path,
                                documentFile.fileName,
                                documentFile.totalPage,
                                documentFile.revision
                        ),
                        document.uploadDt
                ))
                .from(folder)
                .join(document).on(folder.folderId.eq(document.folder.folderId))
                .leftJoin(documentFile).on(document.docId.eq(documentFile.document.docId))
                .where(folder.folderId.eq(folderId))
                .fetch();
    }

    /*
     *
     * 컴파일 타임 안전성: Enum을 사용하면 오타나 잘못된 검색 필드 지정 같은 오류를 컴파일 타임에 잡을 수 있어 실수를 줄일 수 있음
     * <p>
     * 명시적이고 가독성 높은 코드: 각 검색 조건을 명확하게 캡슐화할 수 있어 코드의 의도가 분명해지고, 유지보수 시에도 도움이 됨
     * <p>
     * 일관성 있는 확장: 새로운 조건을 추가할 때 Enum 상수 하나만 추가하면 되므로 확장이 용이


     */

    @Override
    public Optional<DocumentDataResponse> findDocumentDataById(final String docId) {
        final DocumentDataResponse documentDataResponse = queryFactory
                .select(new QDocumentDataResponse(
                        document.docId,
                        document.folder.folderId,
                        document.state,
                        document.progress,
                        new QDocumentFileData(
                                documentFile.docFileId,
                                documentFile.extension,
                                documentFile.path,
                                documentFile.fileName,
//                                documentFile.originalFileName,
                                documentFile.totalPage,
                                documentFile.revision
                        ),
                        document.uploadDt
                ))
                .from(document)
                .leftJoin(documentFile).on(document.docId.eq(documentFile.document.docId))
                .where(document.docId.eq(docId))
                .fetchOne();

        return Optional.ofNullable(documentDataResponse);
    }

    @Override
    public Page<DocumentDatasetResponse> findDocumentDatasetByFolderId(final Long folderId, final String searchField,
                                                                       final String searchInput,
                                                                       final Pageable pageable) {

        // 검색 조건이 있을 경우 동적 조건 생성
        final BooleanExpression condition = folder.folderId.eq(folderId)
                .and(containsSearchField(searchField, searchInput));

        final JPAQuery<DocumentDatasetResponse> base = queryFactory
                .select(new QDocumentDatasetResponse(
                        document.docId,
//                        documentFile.path.concat("\\").concat(documentFile.originalFileName),
                        documentFile.path.concat("\\").concat("tmp"), // TODO. 임시처리
                        documentFile.fileName,
                        documentFile.totalPage,
                        document.state,
                        document.progress,
                        document.uploadDt,
                        documentFile.fileSize
                ))
                .from(folder)
                .innerJoin(document).on(folder.folderId.eq(document.folder.folderId))
                .innerJoin(documentFile).on(document.docId.eq(documentFile.document.docId))
                .where(condition);

        final JPQLQuery<DocumentDatasetResponse> paged = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, base);

        final List<DocumentDatasetResponse> results = paged.fetch();


        return PageableExecutionUtils.getPage(
                results,
                pageable,
                paged::fetchCount
        );
    }

    private BooleanExpression containsSearchField(final String searchField, final String searchInput) {
        if (searchField == null || searchField.trim().isEmpty()) {
            return null;
        }
/* Function<String, BooleanExpression> condition = SEARCH_CONDITIONS.get(searchField);
        if (condition == null) {
            throw DomainExceptionCode.INVALID_SEARCH_FIELD.newInstance(searchField);
        }
        return condition.apply(searchInput);*/

        final DocumentSearchField field = DocumentSearchField.fromString(searchField);
        return field.buildExpression(searchInput);
    }

}
