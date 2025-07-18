package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.FeedbackDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomFeedbackRepositoryImpl implements CustomFeedbackRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<FeedbackDto> fetchFeedbacksByClientId(final String clientId) {

 /*       return queryFactory.select(
                        Projections.constructor(FeedbackDto.class,
//                                knowledgeAccount.account.accountId,
                                question.questionId,
                                question.message,
                                answer.answerId,
                                answer.message,
                                feedback.feedbackType,
                                feedback.comment,
                                similarityDoc.score
                        )
                ).
                from(client)
                .innerJoin(conversation).on(conversation.client.clientId.eq(client.clientId))
                .innerJoin(question).on(conversation.conversationId.eq(question.conversation.conversationId))
                .innerJoin(answer).on(question.questionId.eq(answer.question.questionId))
                .innerJoin(feedback).on(answer.answerId.eq(feedback.answer.answerId))
                .innerJoin(similarityDoc).on(answer.answerId.eq(similarityDoc.answer.answerId))
                .innerJoin(document).on(similarityDoc.document.docId.eq(document.docId))
//                .innerJoin(account).on(account.accountId.eq(projectAccount.account.accountId))
                .where(client.clientId.eq(clientId))
                .fetch();
*/
        return null;

    }
}
