package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Question;
import com.idt.aiowebflux.request.core.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getQuestionByConversation_ConversationId(final Long conversationId);

    Question getQuestionByQuestionId(final Long questionId);

    void deleteQuestionsByConversation_ConversationId(final Long conversationId);

    @Query("""
            SELECT new com.idt.aiowebflux.request.core.Message(q.message, a.message)
                                FROM Question q
                                   LEFT join Answer a ON q.questionId = a.question.questionId
            WHERE q.conversation.conversationId = :conversationId
            ORDER BY q.createDt
            LIMIT :limit
            """)
    List<Message> findQuestionByConversation_ConversationId(@Param("conversationId") Long conversationId,
                                                            @Param("limit") Integer limit);

}
