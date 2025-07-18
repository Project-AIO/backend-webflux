package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.ConversationData;
import com.idt.aiowebflux.dto.ConversationPageDto;
import com.idt.aiowebflux.dto.PageData;
import com.idt.aiowebflux.dto.QConversationData;
import com.idt.aiowebflux.dto.QPageData;
import com.idt.aiowebflux.dto.QRefData;
import com.idt.aiowebflux.dto.RefData;
import com.idt.aiowebflux.entity.Conversation;
import com.idt.aiowebflux.response.core.Pages;
import com.idt.aiowebflux.response.core.Ref;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.idt.aiowebflux.entity.QAnswer.answer;
import static com.idt.aiowebflux.entity.QDocument.document;
import static com.idt.aiowebflux.entity.QDocumentFile.documentFile;
import static com.idt.aiowebflux.entity.QQuestion.question;
import static com.idt.aiowebflux.entity.QSimilarityDoc.similarityDoc;
import static com.idt.aiowebflux.entity.QSimilarityDocPage.similarityDocPage;

@Repository
public class CustomConversationRepositoryImpl extends QuerydslRepositorySupport implements CustomConversationRepository {
    private final JPAQueryFactory queryFactory;

    public CustomConversationRepositoryImpl(final JPAQueryFactory queryFactory) {
        super(Conversation.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ConversationPageDto> findConversationDataById(final Long conversationId, final Pageable pageable) {
        final JPAQuery<ConversationData> base = queryFactory
                .select(
                        new QConversationData(
                                question.questionId,
                                question.message,
                                answer.answerId,
                                answer.message
                        )
                )
                .from(question)
                .join(answer).on(question.questionId.eq(answer.question.questionId))
                .where(question.conversation.conversationId.eq(conversationId));

        final JPQLQuery<ConversationData> paged = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, base);

        final List<ConversationData> conversationData = paged.fetch();

        final List<Long> answerIds = conversationData.stream()
                .map(ConversationData::answerId)
                .toList();

        final List<Pages> page = List.of();
        //key : answerId, value: List<RefData>
        final Map<Long, List<RefData>> refMap = queryFactory
                .select(
                        new QRefData(
                                answer.answerId,
                                documentFile.document.docId,
                                similarityDoc.score,
                                documentFile.extension,
                                documentFile.path,
                                documentFile.fileName,
                                documentFile.totalPage,
                                documentFile.revision,
                                similarityDoc.similarityDocId // similarityDocId 기준으로 page List 구분
                        )
                )
                .from(answer)
                .join(similarityDoc).on(answer.answerId.eq(similarityDoc.answer.answerId))
                .join(document).on(similarityDoc.document.docId.eq(document.docId))
                .join(documentFile).on(documentFile.document.docId.eq(document.docId))
                .where(answer.answerId.in(answerIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(RefData::answerId));

        //key : answerId, value: List<Pages>
        final Map<Long, List<PageData>> docPageMap = queryFactory
                .select(
                        new QPageData(
                                answer.answerId,
                                similarityDoc.similarityDocId,
                                similarityDocPage.page,
                                similarityDocPage.leftX,
                                similarityDocPage.rightX,
                                similarityDocPage.topY,
                                similarityDocPage.bottomY
                        )
                )
                .from(answer)
                .join(similarityDoc).on(answer.answerId.eq(similarityDoc.answer.answerId))
                .join(similarityDocPage).on(similarityDocPage.similarityDoc.similarityDocId.eq(similarityDoc.similarityDocId))
                .where(answer.answerId.in(answerIds))
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(PageData::similarityDocId));

        //List<ConversationPageDto> 구성
        final List<ConversationPageDto> results = conversationData.stream()
                .map(c -> {
                    final List<RefData> refData = refMap.getOrDefault(c.answerId(), List.of());

                    return new ConversationPageDto(
                            c.questionId(),
                            c.questionMessage(),
                            c.answerId(),
                            c.answerMessage(),
                            refData.stream()
                                    .map(r -> {
                                        final List<Pages> pages = docPageMap.get(r.similarityDocId())
                                                .stream()
                                                .map(p -> new Pages(
                                                        p.page(),
                                                        p.leftX(),
                                                        p.rightX(),
                                                        p.topY(),
                                                        p.bottomY()
                                                )).toList();


                                        return new Ref(
                                                r.docId(),
                                                pages,
                                                r.score(),
                                                r.extension(),
                                                r.path(),
                                                r.fileName(),
                                                r.totalPage(),
                                                r.revision()
                                        );
                                    }).toList()
                    );
                }).toList();

        return PageableExecutionUtils.getPage(
                results,
                pageable,
                paged::fetchCount
        );
    }
}
